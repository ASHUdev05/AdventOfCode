using Solutions.Core;

namespace Solutions.Days;

public class Day05 : BaseDay
{
    public override void SolvePart1(string input)
    {
        var lines = input.Split('\n', StringSplitOptions.RemoveEmptyEntries);
        
        // Find the blank line separating ranges from ingredient IDs
        int separatorIndex = -1;
        for (int i = 0; i < lines.Length; i++)
        {
            if (string.IsNullOrWhiteSpace(lines[i]) || !lines[i].Contains('-'))
            {
                // First line without a dash is likely the separator
                if (i > 0 && !lines[i].Contains('-'))
                {
                    separatorIndex = i;
                    break;
                }
            }
        }
        
        // If no clear separator, look for the transition from ranges to single numbers
        if (separatorIndex == -1)
        {
            for (int i = 0; i < lines.Length; i++)
            {
                if (!lines[i].Contains('-'))
                {
                    separatorIndex = i;
                    break;
                }
            }
        }
        
        // Parse fresh ranges
        var freshRanges = new List<(long start, long end)>();
        for (int i = 0; i < separatorIndex; i++)
        {
            var parts = lines[i].Split('-');
            if (parts.Length == 2)
            {
                long start = long.Parse(parts[0].Trim());
                long end = long.Parse(parts[1].Trim());
                freshRanges.Add((start, end));
            }
        }
        
        // Parse available ingredient IDs and check if fresh
        int freshCount = 0;
        for (int i = separatorIndex; i < lines.Length; i++)
        {
            if (long.TryParse(lines[i].Trim(), out long ingredientId))
            {
                bool isFresh = false;
                foreach (var (start, end) in freshRanges)
                {
                    if (ingredientId >= start && ingredientId <= end)
                    {
                        isFresh = true;
                        break;
                    }
                }
                
                if (isFresh)
                {
                    freshCount++;
                }
            }
        }
        
        Console.WriteLine($"Day 5 Part 1: {freshCount}");
    }

    public override void SolvePart2(string input)
    {
        var lines = input.Split('\n', StringSplitOptions.RemoveEmptyEntries);
        
        // Parse fresh ranges (only the first section before available IDs)
        var freshRanges = new List<(long start, long end)>();
        foreach (var line in lines)
        {
            var parts = line.Split('-');
            if (parts.Length == 2)
            {
                long start = long.Parse(parts[0].Trim());
                long end = long.Parse(parts[1].Trim());
                freshRanges.Add((start, end));
            }
        }
        
        // Sort ranges by start position
        freshRanges.Sort((a, b) => a.start.CompareTo(b.start));
        
        // Merge overlapping ranges
        var mergedRanges = new List<(long start, long end)>();
        if (freshRanges.Count > 0)
        {
            var current = freshRanges[0];
            
            for (int i = 1; i < freshRanges.Count; i++)
            {
                var next = freshRanges[i];
                
                // Check if ranges overlap or are adjacent
                if (next.start <= current.end + 1)
                {
                    // Merge: extend current range
                    current = (current.start, Math.Max(current.end, next.end));
                }
                else
                {
                    // No overlap: add current to merged list and start new range
                    mergedRanges.Add(current);
                    current = next;
                }
            }
            
            // Add the last range
            mergedRanges.Add(current);
        }
        
        // Count total IDs in all merged ranges
        long totalFreshIds = 0;
        foreach (var (start, end) in mergedRanges)
        {
            totalFreshIds += (end - start + 1);
        }
        
        Console.WriteLine($"Day 5 Part 2: {totalFreshIds}");
    }
}