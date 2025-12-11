using Solutions.Core;

namespace Solutions.Days;

public class Day11 : BaseDay
{
    public override void SolvePart1(string input)
    {
        var graph = ParseInput(input);
        // Part 1: Count paths from "you" to "out"
        long totalPaths = CountPaths("you", "out", graph, new Dictionary<string, long>());
        Console.WriteLine($"Day 11 Part 1: {totalPaths}");
    }

    public override void SolvePart2(string input)
    {
        var graph = ParseInput(input);

        // Helper to run the count with a fresh cache for every segment
        long GetSegment(string start, string end) 
            => CountPaths(start, end, graph, new Dictionary<string, long>());

        // Scenario 1: svr -> dac -> fft -> out
        long pathViaDacThenFft = GetSegment("svr", "dac") 
                               * GetSegment("dac", "fft") 
                               * GetSegment("fft", "out");

        // Scenario 2: svr -> fft -> dac -> out
        long pathViaFftThenDac = GetSegment("svr", "fft") 
                               * GetSegment("fft", "dac") 
                               * GetSegment("dac", "out");

        long total = pathViaDacThenFft + pathViaFftThenDac;

        Console.WriteLine($"Day 11 Part 2: {total}");
    }

    private Dictionary<string, List<string>> ParseInput(string input)
    {
        var graph = new Dictionary<string, List<string>>();
        var lines = input.Split(new[] { '\r', '\n' }, StringSplitOptions.RemoveEmptyEntries);

        foreach (var line in lines)
        {
            var parts = line.Split(':');
            var source = parts[0].Trim();
            var targets = parts[1].Trim().Split(' ', StringSplitOptions.RemoveEmptyEntries);

            if (!graph.ContainsKey(source))
                graph[source] = new List<string>();
            
            graph[source].AddRange(targets);
        }
        return graph;
    }

    // Updated to accept a specific 'target' node
    private long CountPaths(string current, string target, Dictionary<string, List<string>> graph, Dictionary<string, long> memo)
    {
        if (current == target) return 1;
        if (memo.ContainsKey(current)) return memo[current];
        
        // If dead end and not target, path is invalid (0)
        if (!graph.ContainsKey(current)) return 0;

        long pathCount = 0;
        foreach (var neighbor in graph[current])
        {
            pathCount += CountPaths(neighbor, target, graph, memo);
        }

        memo[current] = pathCount;
        return pathCount;
    }
}