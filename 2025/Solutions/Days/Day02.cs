using Solutions.Core;

namespace Solutions.Days;

public class Day02 : BaseDay
{
    public override void SolvePart1(string input)
    {
        var lines = input.Split('\n');

        // Parse all ranges
        var ranges = lines
            .SelectMany(line => line.Split(',', StringSplitOptions.RemoveEmptyEntries))
            .Select(range => range.Split('-'))
            .Select(parts => (start: long.Parse(parts[0]), end: long.Parse(parts[1])))
            .ToList();

        long sumOfInvalidIds = 0;

        foreach (var (start, end) in ranges)
        {
            for (long id = start; id <= end; id++)
            {
                if (IsInvalidId(id))
                {
                    sumOfInvalidIds += id;
                }
            }
        }

        Console.WriteLine($"Day 2 Part 1: {sumOfInvalidIds}");
    }

    private bool IsInvalidId(long id)
    {
        string idStr = id.ToString();
        int len = idStr.Length;

        // Must have even length to be split in half
        if (len % 2 != 0)
            return false;

        int halfLen = len / 2;
        string firstHalf = idStr.Substring(0, halfLen);
        string secondHalf = idStr.Substring(halfLen);

        // Check if the two halves are identical
        return firstHalf == secondHalf;
    }

    public override void SolvePart2(string input)
    {
        var lines = input.Split('\n');

        // Parse all ranges
        var ranges = lines
            .SelectMany(line => line.Split(',', StringSplitOptions.RemoveEmptyEntries))
            .Select(range => range.Split('-'))
            .Select(parts => (start: long.Parse(parts[0]), end: long.Parse(parts[1])))
            .ToList();

        long sumOfInvalidIds = 0;

        foreach (var (start, end) in ranges)
        {
            for (long id = start; id <= end; id++)
            {
                if (IsInvalidIdPart2(id))
                {
                    sumOfInvalidIds += id;
                }
            }
        }

        Console.WriteLine($"Day 2 Part 2: {sumOfInvalidIds}");
    }

    private bool IsInvalidIdPart2(long id)
    {
        string idStr = id.ToString();
        int len = idStr.Length;

        // Try all possible pattern lengths (from 1 to len/2)
        // A pattern repeated at least twice means the pattern length is at most len/2
        for (int patternLen = 1; patternLen <= len / 2; patternLen++)
        {
            // Check if the entire string can be formed by repeating this pattern
            if (len % patternLen == 0)
            {
                string pattern = idStr.Substring(0, patternLen);
                bool isRepeating = true;

                // Check if the entire ID is made of this pattern repeated
                for (int i = patternLen; i < len; i += patternLen)
                {
                    string segment = idStr.Substring(i, patternLen);
                    if (segment != pattern)
                    {
                        isRepeating = false;
                        break;
                    }
                }

                if (isRepeating)
                {
                    return true; // Found a repeating pattern
                }
            }
        }

        return false;
    }
}
