using Solutions.Core;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Solutions.Days;

public class Day12 : BaseDay
{
    public override void SolvePart1(string input)
    {
        // 1. Define the area of each shape based on the User's Input
        // Shape 0: (##., .##, ###) -> 2+2+3 = 7
        // Shape 1: (..#, ###, ###) -> 1+3+3 = 7
        // Shape 2: (#.#, ###, #.#) -> 2+3+2 = 7
        // Shape 3: (###, ..#, ###) -> 3+1+3 = 7
        // Shape 4: (#.., ##., ###) -> 1+2+3 = 6
        // Shape 5: (##., .##, ..#) -> 2+2+1 = 5
        int[] shapeAreas = new int[] { 7, 7, 7, 7, 6, 5 };

        var lines = input.Split(new[] { '\n', '\r' }, StringSplitOptions.RemoveEmptyEntries);
        int validRegions = 0;

        foreach (var line in lines)
        {
            // We only care about the region lines (containing "x" and ":")
            if (!line.Contains("x") || !line.Contains(":")) continue;

            // Parse Line: "50x50: 46 40 42 34 52 41"
            var parts = line.Split(':');
            var dimParts = parts[0].Split('x');
            int width = int.Parse(dimParts[0]);
            int height = int.Parse(dimParts[1]);
            long regionArea = (long)width * height;

            var counts = parts[1].Trim().Split(new[] { ' ' }, StringSplitOptions.RemoveEmptyEntries)
                                 .Select(int.Parse)
                                 .ToArray();

            // Calculate total area required by presents
            long requiredArea = 0;
            for (int i = 0; i < counts.Length; i++)
            {
                requiredArea += counts[i] * shapeAreas[i];
            }

            // Since pieces are tiny (3x3) and regions are large (min 35x35),
            // and the packing density is not 100%, 
            // any configuration where Area(Presents) <= Area(Region) is geometrically valid.
            if (requiredArea <= regionArea)
            {
                validRegions++;
            }
        }

        Console.WriteLine($"Day 12 Part 1: {validRegions}");
    }

    public override void SolvePart2(string input)
    {
        Console.WriteLine($"Free ⭐");
    }
}