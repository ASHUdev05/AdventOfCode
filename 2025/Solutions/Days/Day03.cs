using Solutions.Core;

namespace Solutions.Days;

public class Day03 : BaseDay
{
    public override void SolvePart1(string input)
    {
        var banks = input.Split('\n', StringSplitOptions.RemoveEmptyEntries);

        var totJolts = 0;
        foreach (var bank in banks)
        {
            int maxJoltage = GetMaxJoltage(bank);
            totJolts += maxJoltage;
        }

        Console.WriteLine($"Day 3 Part 1: {totJolts}");
    }

    private int GetMaxJoltage(string bank)
    {
        int maxJoltage = 0;

        // Try every pair of positions (i, j) where i < j
        for (int i = 0; i < bank.Length - 1; i++)
        {
            for (int j = i + 1; j < bank.Length; j++)
            {
                int tens = int.Parse(bank[i].ToString());
                int ones = int.Parse(bank[j].ToString());
                int joltage = tens * 10 + ones;

                maxJoltage = Math.Max(maxJoltage, joltage);
            }
        }

        return maxJoltage;
    }

    public override void SolvePart2(string input)
    {
        var banks = input.Split('\n', StringSplitOptions.RemoveEmptyEntries);

        long totJolts = 0;
        foreach (var bank in banks)
        {
            long maxJoltage = GetMaxJoltage12(bank);
            totJolts += maxJoltage;
        }

        Console.WriteLine($"Day 3 Part 2: {totJolts}");
    }

    private long GetMaxJoltage12(string bank)
    {
        int batteriesToKeep = 12;
        int batteriesToSkip = bank.Length - batteriesToKeep;

        // We need to skip the smallest digits while maintaining order
        // Use a greedy approach: at each position, skip if we can afford to
        // and if skipping helps us keep larger digits later

        List<char> result = new List<char>();
        int skipped = 0;

        for (int i = 0; i < bank.Length; i++)
        {
            // While we have digits in result and can still skip more,
            // and the current digit is larger than the last digit we kept,
            // remove the last digit (we can do better)
            while (result.Count > 0 &&
                   skipped < batteriesToSkip &&
                   bank[i] > result[result.Count - 1])
            {
                result.RemoveAt(result.Count - 1);
                skipped++;
            }

            result.Add(bank[i]);
        }

        // We might have kept too many - trim to exactly 12
        while (result.Count > batteriesToKeep)
        {
            result.RemoveAt(result.Count - 1);
        }

        string joltageStr = new string(result.ToArray());
        return long.Parse(joltageStr);
    }
}