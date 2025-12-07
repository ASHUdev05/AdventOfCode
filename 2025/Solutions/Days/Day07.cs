using Solutions.Core;
using System.Collections.Generic;

namespace Solutions.Days;

public class Day07 : BaseDay
{
    public override void SolvePart1(string input)
    {
        var lines = input.Split('\n', StringSplitOptions.RemoveEmptyEntries);
        var grid = lines.Select(l => l.ToCharArray()).ToArray();
        
        // Find the starting position S
        int startRow = -1, startCol = -1;
        for (int r = 0; r < grid.Length; r++)
        {
            for (int c = 0; c < grid[r].Length; c++)
            {
                if (grid[r][c] == 'S')
                {
                    startRow = r;
                    startCol = c;
                    break;
                }
            }
            if (startRow != -1) break;
        }
        
        int splitCount = CountSplits(grid, startRow, startCol);
        Console.WriteLine($"Day 7 Part 1: {splitCount}");
    }
    
    private int CountSplits(char[][] grid, int startRow, int startCol)
    {
        int splits = 0;
        var queue = new Queue<(int row, int col)>();
        var visited = new HashSet<(int, int)>();
        
        // Start with the initial beam
        queue.Enqueue((startRow, startCol));
        
        while (queue.Count > 0)
        {
            var (row, col) = queue.Dequeue();
            
            // Move downward until we hit a splitter or exit
            for (int r = row + 1; r < grid.Length; r++)
            {
                if (grid[r][col] == '^')
                {
                    // Hit a splitter - only count if we haven't processed this splitter before
                    if (!visited.Contains((r, col)))
                    {
                        visited.Add((r, col));
                        splits++;
                        
                        // Create new beams from left and right of splitter
                        // They continue downward from those positions
                        if (col > 0)
                        {
                            queue.Enqueue((r, col - 1));
                        }
                        if (col < grid[r].Length - 1)
                        {
                            queue.Enqueue((r, col + 1));
                        }
                    }
                    break; // This beam stops here
                }
            }
        }
        
        return splits;
    }

    public override void SolvePart2(string input)
    {
        var lines = input.Split('\n', StringSplitOptions.RemoveEmptyEntries);
        var grid = lines.Select(l => l.ToCharArray()).ToArray();
        
        // Find the starting position S
        int startRow = -1, startCol = -1;
        for (int r = 0; r < grid.Length; r++)
        {
            for (int c = 0; c < grid[r].Length; c++)
            {
                if (grid[r][c] == 'S')
                {
                    startRow = r;
                    startCol = c;
                    break;
                }
            }
            if (startRow != -1) break;
        }
        
        long timelineCount = CountTimelines(grid, startRow, startCol);
        Console.WriteLine($"Day 7 Part 2: {timelineCount}");
    }
    
    private long CountTimelines(char[][] grid, int startRow, int startCol)
    {
        // Memoization: for each position, store the number of timelines that reach the bottom
        var memo = new Dictionary<(int row, int col), long>();
        
        return CountTimelinesDFS(grid, startRow, startCol, memo);
    }
    
    private long CountTimelinesDFS(char[][] grid, int row, int col, Dictionary<(int, int), long> memo)
    {
        // Check if we've already computed this
        if (memo.ContainsKey((row, col)))
        {
            return memo[(row, col)];
        }
        
        // Base case: if we're at or past the bottom, this is one complete timeline
        if (row >= grid.Length)
        {
            return 1;
        }
        
        // Check if column is out of bounds
        if (col < 0 || col >= grid[0].Length)
        {
            return 1; // Exited the side, one timeline
        }
        
        long totalTimelines = 0;
        
        // Move downward until we hit a splitter or exit
        int currentRow = row;
        while (currentRow < grid.Length)
        {
            if (grid[currentRow][col] == '^')
            {
                // Hit a splitter - particle takes both paths
                long leftTimelines = CountTimelinesDFS(grid, currentRow + 1, col - 1, memo);
                long rightTimelines = CountTimelinesDFS(grid, currentRow + 1, col + 1, memo);
                totalTimelines = leftTimelines + rightTimelines;
                break;
            }
            currentRow++;
        }
        
        // If we exited without hitting a splitter, that's one timeline
        if (currentRow >= grid.Length)
        {
            totalTimelines = 1;
        }
        
        memo[(row, col)] = totalTimelines;
        return totalTimelines;
    }
}