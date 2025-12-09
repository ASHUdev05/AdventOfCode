using Solutions.Core;

namespace Solutions.Days;

public class Day09 : BaseDay
{
    public override void SolvePart1(string input)
    {
        var tiles = ParseInput(input);
        long maxArea = 0;
        
        for (int i = 0; i < tiles.Count; i++)
        {
            for (int j = i + 1; j < tiles.Count; j++)
            {
                var t1 = tiles[i];
                var t2 = tiles[j];
                
                long w = Math.Abs(t1.x - t2.x) + 1;
                long h = Math.Abs(t1.y - t2.y) + 1;
                maxArea = Math.Max(maxArea, w * h);
            }
        }
        Console.WriteLine($"Day 9 Part 1: {maxArea}");
    }

    public override void SolvePart2(string input)
    {
        var poly = ParseInput(input);
        long maxArea = 0;
        
        // Pre-calculate edges for performance
        var edges = new List<(int x1, int y1, int x2, int y2)>();
        for (int i = 0; i < poly.Count; i++)
        {
            var p1 = poly[i];
            var p2 = poly[(i + 1) % poly.Count];
            edges.Add((p1.x, p1.y, p2.x, p2.y));
        }

        for (int i = 0; i < poly.Count; i++)
        {
            for (int j = i + 1; j < poly.Count; j++)
            {
                var t1 = poly[i];
                var t2 = poly[j];
                
                int minX = Math.Min(t1.x, t2.x);
                int maxX = Math.Max(t1.x, t2.x);
                int minY = Math.Min(t1.y, t2.y);
                int maxY = Math.Max(t1.y, t2.y);
                
                long area = (long)(maxX - minX + 1) * (maxY - minY + 1);
                
                if (area <= maxArea) continue;

                if (IsValidRect(minX, maxX, minY, maxY, poly, edges))
                {
                    maxArea = area;
                }
            }
        }
        
        Console.WriteLine($"Day 9 Part 2: {maxArea}");
    }

    private bool IsValidRect(int rMinX, int rMaxX, int rMinY, int rMaxY, 
                             List<(int x, int y)> poly, 
                             List<(int x1, int y1, int x2, int y2)> edges)
    {
        // 1. VERTEX CHECK
        // No polygon vertex can be strictly inside the rectangle.
        foreach (var p in poly)
        {
            if (p.x > rMinX && p.x < rMaxX && p.y > rMinY && p.y < rMaxY)
                return false;
        }

        // 2. EDGE INTERSECTION CHECK
        // Since polygon is rectilinear, we check for strict crossing.
        foreach (var edge in edges)
        {
            // Vertical Polygon Edge? (x1 == x2)
            if (edge.x1 == edge.x2)
            {
                int edgeX = edge.x1;
                int edgeYMin = Math.Min(edge.y1, edge.y2);
                int edgeYMax = Math.Max(edge.y1, edge.y2);

                // Does this vertical line cut through the rectangle horizontally?
                // Strict check: Edge X is inside rectangle (not on border)
                if (edgeX > rMinX && edgeX < rMaxX)
                {
                    // And the Y ranges overlap strictly
                    if (Math.Max(rMinY, edgeYMin) < Math.Min(rMaxY, edgeYMax))
                        return false;
                }
            }
            // Horizontal Polygon Edge? (y1 == y2)
            else
            {
                int edgeY = edge.y1;
                int edgeXMin = Math.Min(edge.x1, edge.x2);
                int edgeXMax = Math.Max(edge.x1, edge.x2);

                // Does this horizontal line cut through the rectangle vertically?
                // Strict check: Edge Y is inside rectangle (not on border)
                if (edgeY > rMinY && edgeY < rMaxY)
                {
                    // And the X ranges overlap strictly
                    if (Math.Max(rMinX, edgeXMin) < Math.Min(rMaxX, edgeXMax))
                        return false;
                }
            }
        }

        // 3. CENTER INCLUSION CHECK
        // If no vertices are inside and no edges cross, the rectangle is either 
        // fully inside or fully outside. We test the center point.
        
        // We add a small epsilon (0.1) to avoid landing exactly on integer grid lines,
        // which makes ray casting simpler.
        double cx = (rMinX + rMaxX) / 2.0 + 0.1;
        double cy = (rMinY + rMaxY) / 2.0 + 0.1;

        return IsPointInPolygon(cx, cy, poly);
    }

    private bool IsPointInPolygon(double x, double y, List<(int x, int y)> poly)
    {
        bool inside = false;
        for (int i = 0, j = poly.Count - 1; i < poly.Count; j = i++)
        {
            // Ray Casting Algorithm
            // If ray intersects edge, toggle inside/outside
            if (((poly[i].y > y) != (poly[j].y > y)) &&
                (x < (poly[j].x - poly[i].x) * (y - poly[i].y) / (double)(poly[j].y - poly[i].y) + poly[i].x))
            {
                inside = !inside;
            }
        }
        return inside;
    }

    private List<(int x, int y)> ParseInput(string input)
    {
        var lines = input.Split('\n', StringSplitOptions.RemoveEmptyEntries);
        var list = new List<(int x, int y)>();
        foreach (var line in lines)
        {
            var parts = line.Split(',');
            list.Add((int.Parse(parts[0]), int.Parse(parts[1])));
        }
        return list;
    }
}