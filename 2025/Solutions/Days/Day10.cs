using Solutions.Core;
using System.Text.RegularExpressions;

namespace Solutions.Days;

public class Day10 : BaseDay
{
    public override void SolvePart1(string input)
    {
        // Part 1: Toggle lights (Bitmask)
        var lines = input.Split('\n', StringSplitOptions.RemoveEmptyEntries);
        long totalPresses = 0;
        foreach (var line in lines) totalPresses += SolvePart1Bitmask(line);
        Console.WriteLine($"Day 10 Part 1: {totalPresses}");
    }

    public override void SolvePart2(string input)
    {
        // Part 2: Exact Targets (Linear Algebra + Search)
        var lines = input.Split('\n', StringSplitOptions.RemoveEmptyEntries);
        long totalPresses = 0;
        foreach (var line in lines) totalPresses += SolvePart2LinearAlgebra(line);
        Console.WriteLine($"Day 10 Part 2: {totalPresses}");
    }
    
    private int SolvePart1Bitmask(string line)
    {
        var bracketMatch = Regex.Match(line, @"\[([.#]+)\]");
        string lights = bracketMatch.Groups[1].Value;
        int numLights = lights.Length;
        int targetMask = 0;
        for (int i = 0; i < numLights; i++) if (lights[i] == '#') targetMask |= (1 << i);
        
        var buttonMatches = Regex.Matches(line, @"\(([0-9,]+)\)");
        int[] buttonMasks = new int[buttonMatches.Count];
        for (int i = 0; i < buttonMatches.Count; i++)
        {
            foreach (var idx in buttonMatches[i].Groups[1].Value.Split(',').Select(int.Parse))
                buttonMasks[i] |= (1 << idx);
        }
        
        int numButtons = buttonMasks.Length;
        int minPresses = int.MaxValue;
        
        // Brute force 2^N for small N
        for (int i = 0; i < (1 << numButtons); i++)
        {
            int currentMask = 0;
            int presses = 0;
            for (int b = 0; b < numButtons; b++)
            {
                if (((i >> b) & 1) == 1)
                {
                    currentMask ^= buttonMasks[b];
                    presses++;
                }
            }
            if (currentMask == targetMask) minPresses = Math.Min(minPresses, presses);
        }
        return minPresses == int.MaxValue ? 0 : minPresses;
    }

    private long SolvePart2LinearAlgebra(string line)
    {
        // 1. Parse Inputs
        var braceMatch = Regex.Match(line, @"\{([0-9,]+)\}");
        double[] targets = braceMatch.Groups[1].Value.Split(',').Select(double.Parse).ToArray();

        var buttonMatches = Regex.Matches(line, @"\(([0-9,]+)\)");
        List<int[]> buttons = new List<int[]>();
        foreach (Match match in buttonMatches)
            buttons.Add(match.Groups[1].Value.Split(',').Select(int.Parse).ToArray());

        int numRows = targets.Length;     // Equations
        int numCols = buttons.Count;      // Variables

        // 2. Build Matrix [A | b]
        double[,] matrix = new double[numRows, numCols + 1];
        for (int c = 0; c < numCols; c++)
        {
            foreach (int r in buttons[c]) if (r < numRows) matrix[r, c] = 1.0;
        }
        for (int r = 0; r < numRows; r++) matrix[r, numCols] = targets[r];

        // 3. Gaussian Elimination to RREF
        int pivotRow = 0;
        int[] pivotColToRow = Enumerable.Repeat(-1, numCols).ToArray();
        List<int> freeCols = new List<int>();

        for (int col = 0; col < numCols; col++)
        {
            if (pivotRow >= numRows)
            {
                freeCols.Add(col);
                continue;
            }

            int sel = -1;
            for (int row = pivotRow; row < numRows; row++)
            {
                if (Math.Abs(matrix[row, col]) > 1e-9) { sel = row; break; }
            }

            if (sel == -1)
            {
                freeCols.Add(col);
                continue;
            }

            // Swap and Normalize
            for (int k = col; k <= numCols; k++) 
                (matrix[pivotRow, k], matrix[sel, k]) = (matrix[sel, k], matrix[pivotRow, k]);
            
            double val = matrix[pivotRow, col];
            for (int k = col; k <= numCols; k++) matrix[pivotRow, k] /= val;

            for (int row = 0; row < numRows; row++)
            {
                if (row != pivotRow && Math.Abs(matrix[row, col]) > 1e-9)
                {
                    double factor = matrix[row, col];
                    for (int k = col; k <= numCols; k++) matrix[row, k] -= factor * matrix[pivotRow, k];
                }
            }

            pivotColToRow[col] = pivotRow;
            pivotRow++;
        }
        
        // Check for inconsistency (0 = NonZero)
        for (int r = pivotRow; r < numRows; r++)
        {
            if (Math.Abs(matrix[r, numCols]) > 1e-5) return 0; // Impossible
        }

        // 4. Search Free Variables
        // We iterate possible values for free variables and calculate pivots
        long minTotalPresses = long.MaxValue;
        
        // Calculate upper bounds for free variables based on targets
        // If a button affects a counter with target 5, we can't press it > 5 times
        int[] freeVarBounds = new int[freeCols.Count];
        for(int i=0; i<freeCols.Count; i++)
        {
            int col = freeCols[i];
            int limit = 1000; // Arbitrary safe upper limit
            foreach (int r in buttons[col]) 
                if (r < targets.Length) limit = Math.Min(limit, (int)targets[r]);
            freeVarBounds[i] = limit;
        }

        void Search(int freeIdx, long[] currentSolution)
        {
            if (freeIdx == freeCols.Count)
            {
                // All free vars assigned, calculate pivots
                long currentSum = 0;
                bool valid = true;

                // Calculate cost of free vars
                foreach (int fc in freeCols) currentSum += currentSolution[fc];

                // Calculate Pivots
                for (int c = 0; c < numCols; c++)
                {
                    if (pivotColToRow[c] != -1) // It's a pivot
                    {
                        int r = pivotColToRow[c];
                        double val = matrix[r, numCols];
                        
                        // Subtract contribution of free variables
                        for (int fi = 0; fi < freeCols.Count; fi++)
                        {
                            int fCol = freeCols[fi];
                            val -= matrix[r, fCol] * currentSolution[fCol];
                        }

                        // Check validity
                        if (val < -1e-5) { valid = false; break; }
                        long rounded = (long)Math.Round(val);
                        if (Math.Abs(val - rounded) > 1e-4) { valid = false; break; }
                        
                        currentSolution[c] = rounded;
                        currentSum += rounded;
                    }
                }

                if (valid)
                {
                    minTotalPresses = Math.Min(minTotalPresses, currentSum);
                }
                return;
            }

            int col = freeCols[freeIdx];
            for (int val = 0; val <= freeVarBounds[freeIdx]; val++)
            {
                currentSolution[col] = val;
                Search(freeIdx + 1, currentSolution);
            }
        }

        Search(0, new long[numCols]);

        return minTotalPresses == long.MaxValue ? 0 : minTotalPresses;
    }
}