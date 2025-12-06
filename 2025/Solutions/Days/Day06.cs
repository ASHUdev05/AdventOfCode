using Solutions.Core;

namespace Solutions.Days;

public class Day06 : BaseDay
{
    public override void SolvePart1(string input)
    {
        var lines = input.Split('\n', StringSplitOptions.RemoveEmptyEntries);
        
        if (lines.Length == 0)
        {
            Console.WriteLine($"Day 6 Part 1: 0");
            return;
        }
        
        // Find the maximum line length to handle ragged arrays
        int maxLength = lines.Max(l => l.Length);
        
        // Pad all lines to the same length
        for (int i = 0; i < lines.Length; i++)
        {
            if (lines[i].Length < maxLength)
            {
                lines[i] = lines[i].PadRight(maxLength);
            }
        }
        
        // Parse problems column by column
        var problems = new List<Problem>();
        int col = 0;
        
        while (col < maxLength)
        {
            // Skip leading spaces
            while (col < maxLength && IsColumnEmpty(lines, col))
            {
                col++;
            }
            
            if (col >= maxLength) break;
            
            // Find the extent of this problem (until we hit an empty column)
            int startCol = col;
            int endCol = col;
            
            while (endCol < maxLength && !IsColumnEmpty(lines, endCol))
            {
                endCol++;
            }
            
            // Extract this problem
            var problem = ExtractProblem(lines, startCol, endCol);
            if (problem != null)
            {
                problems.Add(problem);
            }
            
            col = endCol;
        }
        
        // Calculate the grand total
        long grandTotal = 0;
        foreach (var problem in problems)
        {
            long result = problem.Calculate();
            grandTotal += result;
        }
        
        Console.WriteLine($"Day 6 Part 1: {grandTotal}");
    }
    
    private bool IsColumnEmpty(string[] lines, int col)
    {
        for (int row = 0; row < lines.Length; row++)
        {
            if (col < lines[row].Length && lines[row][col] != ' ')
            {
                return false;
            }
        }
        return true;
    }
    
    private Problem? ExtractProblem(string[] lines, int startCol, int endCol)
    {
        var numbers = new List<long>();
        char operation = ' ';
        
        // Process all rows except the last (which should contain the operation)
        for (int row = 0; row < lines.Length - 1; row++)
        {
            string segment = lines[row].Substring(startCol, endCol - startCol).Trim();
            if (!string.IsNullOrEmpty(segment) && long.TryParse(segment, out long num))
            {
                numbers.Add(num);
            }
        }
        
        // Get the operation from the last row
        string opRow = lines[lines.Length - 1].Substring(startCol, endCol - startCol).Trim();
        if (opRow.Length > 0 && (opRow[0] == '+' || opRow[0] == '*'))
        {
            operation = opRow[0];
        }
        
        if (numbers.Count > 0 && operation != ' ')
        {
            return new Problem(numbers, operation);
        }
        
        return null;
    }

    public override void SolvePart2(string input)
    {
        var lines = input.Split('\n', StringSplitOptions.RemoveEmptyEntries);
        
        if (lines.Length == 0)
        {
            Console.WriteLine($"Day 6 Part 2: 0");
            return;
        }
        
        // Find the maximum line length
        int maxLength = lines.Max(l => l.Length);
        
        // Pad all lines to the same length
        for (int i = 0; i < lines.Length; i++)
        {
            if (lines[i].Length < maxLength)
            {
                lines[i] = lines[i].PadRight(maxLength);
            }
        }
        
        // Parse problems reading right-to-left, column by column
        var problems = new List<Problem>();
        int col = maxLength - 1;
        
        while (col >= 0)
        {
            // Skip trailing spaces (going right-to-left)
            while (col >= 0 && IsColumnEmpty(lines, col))
            {
                col--;
            }
            
            if (col < 0) break;
            
            // Find the extent of this problem (until we hit an empty column)
            int endCol = col; // rightmost column of problem
            int startCol = col;
            
            while (startCol >= 0 && !IsColumnEmpty(lines, startCol))
            {
                startCol--;
            }
            startCol++; // Move back to first non-empty column
            
            // Extract this problem reading columns as digit positions
            var problem = ExtractProblemPart2(lines, startCol, endCol + 1);
            if (problem != null)
            {
                problems.Add(problem);
            }
            
            col = startCol - 1;
        }
        
        // Calculate the grand total
        long grandTotal = 0;
        foreach (var problem in problems)
        {
            long result = problem.Calculate();
            grandTotal += result;
        }
        
        Console.WriteLine($"Day 6 Part 2: {grandTotal}");
    }
    
    private Problem? ExtractProblemPart2(string[] lines, int startCol, int endCol)
    {
        char operation = ' ';
        
        // Get the operation from the last row
        string opSegment = lines[lines.Length - 1].Substring(startCol, endCol - startCol);
        for (int i = 0; i < opSegment.Length; i++)
        {
            if (opSegment[i] == '+' || opSegment[i] == '*')
            {
                operation = opSegment[i];
                break;
            }
        }
        
        // For Part 2: Each COLUMN forms a number by reading top-to-bottom
        // Most significant digit at top, least significant at bottom
        // Reading problems right-to-left means rightmost columns are processed first
        int numRows = lines.Length - 1; // Exclude operation row
        var numbers = new List<long>();
        
        // Process each column in the problem (right-to-left order)
        for (int col = endCol - 1; col >= startCol; col--)
        {
            long number = 0;
            bool hasDigit = false;
            
            // Read this column top-to-bottom to form one number
            for (int row = 0; row < numRows; row++)
            {
                char ch = lines[row][col];
                if (char.IsDigit(ch))
                {
                    hasDigit = true;
                    // Top digit is most significant, so multiply existing and add new
                    number = number * 10 + (ch - '0');
                }
            }
            
            if (hasDigit)
            {
                numbers.Add(number);
            }
        }
        
        if (numbers.Count > 0 && operation != ' ')
        {
            return new Problem(numbers, operation);
        }
        
        return null;
    }
    
    private class Problem
    {
        public List<long> Numbers { get; }
        public char Operation { get; }
        
        public Problem(List<long> numbers, char operation)
        {
            Numbers = numbers;
            Operation = operation;
        }
        
        public long Calculate()
        {
            if (Numbers.Count == 0) return 0;
            
            long result = Numbers[0];
            for (int i = 1; i < Numbers.Count; i++)
            {
                if (Operation == '+')
                {
                    result += Numbers[i];
                }
                else if (Operation == '*')
                {
                    result *= Numbers[i];
                }
            }
            
            return result;
        }
    }
}