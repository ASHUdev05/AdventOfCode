using Solutions.Core;

namespace Solutions.Days;

public class Day04 : BaseDay
{
    public override void SolvePart1(string input)
    {
        var lines = input.Split('\n', StringSplitOptions.RemoveEmptyEntries);
        var grid = lines.Select(line => line.ToCharArray()).ToArray();

        int rows = grid.Length;
        int cols = grid[0].Length;
        int accessibleCount = 0;

        // Directions for 8 adjacent positions (including diagonals)
        int[] dx = { -1, -1, -1, 0, 0, 1, 1, 1 };
        int[] dy = { -1, 0, 1, -1, 1, -1, 0, 1 };

        // Check each position in the grid
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                // Only check positions with paper rolls
                if (grid[i][j] == '@')
                {
                    int adjacentRolls = 0;

                    // Count adjacent paper rolls
                    for (int d = 0; d < 8; d++)
                    {
                        int ni = i + dx[d];
                        int nj = j + dy[d];

                        // Check if position is valid and has a paper roll
                        if (ni >= 0 && ni < rows && nj >= 0 && nj < cols && grid[ni][nj] == '@')
                        {
                            adjacentRolls++;
                        }
                    }

                    // Roll is accessible if it has fewer than 4 adjacent rolls
                    if (adjacentRolls < 4)
                    {
                        accessibleCount++;
                    }
                }
            }
        }

        Console.WriteLine($"Day 4 Part 1: {accessibleCount}");
    }

    public override void SolvePart2(string input)
    {
        var lines = input.Split('\n', StringSplitOptions.RemoveEmptyEntries);
        var grid = lines.Select(line => line.ToCharArray()).ToArray();

        int rows = grid.Length;
        int cols = grid[0].Length;
        int totalRemoved = 0;

        // Directions for 8 adjacent positions (including diagonals)
        int[] dx = { -1, -1, -1, 0, 0, 1, 1, 1 };
        int[] dy = { -1, 0, 1, -1, 1, -1, 0, 1 };

        // Keep removing rolls until no more can be removed
        bool removedAny = true;
        while (removedAny)
        {
            removedAny = false;
            var toRemove = new List<(int, int)>();

            // Find all accessible rolls in current state
            for (int i = 0; i < rows; i++)
            {
                for (int j = 0; j < cols; j++)
                {
                    if (grid[i][j] == '@')
                    {
                        int adjacentRolls = 0;

                        // Count adjacent paper rolls
                        for (int d = 0; d < 8; d++)
                        {
                            int ni = i + dx[d];
                            int nj = j + dy[d];

                            if (ni >= 0 && ni < rows && nj >= 0 && nj < cols && grid[ni][nj] == '@')
                            {
                                adjacentRolls++;
                            }
                        }

                        // Roll is accessible if it has fewer than 4 adjacent rolls
                        if (adjacentRolls < 4)
                        {
                            toRemove.Add((i, j));
                        }
                    }
                }
            }

            // Remove all accessible rolls
            if (toRemove.Count > 0)
            {
                removedAny = true;
                totalRemoved += toRemove.Count;

                foreach (var (i, j) in toRemove)
                {
                    grid[i][j] = '.';
                }
            }
        }

        Console.WriteLine($"Day 4 Part 2: {totalRemoved}");
    }
}
