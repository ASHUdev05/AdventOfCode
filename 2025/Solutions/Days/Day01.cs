using Solutions.Core;

namespace Solutions.Days;

public class Day01 : BaseDay
{
    public override void SolvePart1(string input)
    {
        var lines = input.Split('\n');

        int start = 50;
        int counter = 0;
        foreach (var line in lines)
        {
            int value = int.Parse(line[1..]);
            switch (line.ToCharArray()[0])
            {
                case 'L':
                    // decrement
                    start -= value;
                    break;
                case 'R':
                    // increment
                    start += value;
                    break;
            }

            // normalize to 0-99
            start = (start + 100) % 100;

            counter = start == 0 ? counter + 1 : counter;
        }
        Console.WriteLine($"Day 1 Part 1: {counter}");
    }

    public override void SolvePart2(string input)
    {
        var lines = input.Split('\n');

        int start = 50;
        int counter = 0;

        foreach (var line in lines)
        {
            int value = int.Parse(line[1..]);

            switch (line.ToCharArray()[0])
            {
                case 'L':
                    if ((start - (value % 100)) <= 0 && start != 0)
                    {
                        counter++;
                    }

                    start = (start - value) % 100;
                    if (start < 0) start += 100;

                    counter += value / 100;
                    break;

                case 'R':
                    if ((start + (value % 100)) >= 100)
                    {
                        counter++;
                    }

                    // normalize to 0-99
                    start = (start + value) % 100;

                    counter += value / 100;
                    break;
            }
        }
        Console.WriteLine($"Day 1 Part 2: {counter}");
    }
}
