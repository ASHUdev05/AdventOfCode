using Solutions.Core;

namespace Solutions.Days;

public class Day08 : BaseDay
{
    private class UnionFind
    {
        private int[] parent;
        private int[] size;
        
        public UnionFind(int n)
        {
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++)
            {
                parent[i] = i;
                size[i] = 1;
            }
        }
        
        public int Find(int x)
        {
            if (parent[x] != x)
            {
                parent[x] = Find(parent[x]); // Path compression
            }
            return parent[x];
        }
        
        public bool Union(int x, int y)
        {
            int rootX = Find(x);
            int rootY = Find(y);
            
            if (rootX == rootY)
            {
                return false; // Already in same set
            }
            
            // Union by size
            if (size[rootX] < size[rootY])
            {
                parent[rootX] = rootY;
                size[rootY] += size[rootX];
            }
            else
            {
                parent[rootY] = rootX;
                size[rootX] += size[rootY];
            }
            
            return true;
        }
        
        public int GetComponentCount()
        {
            var roots = new HashSet<int>();
            for (int i = 0; i < parent.Length; i++)
            {
                roots.Add(Find(i));
            }
            return roots.Count;
        }
        
        public Dictionary<int, int> GetComponentSizes()
        {
            var sizes = new Dictionary<int, int>();
            for (int i = 0; i < parent.Length; i++)
            {
                int root = Find(i);
                if (!sizes.ContainsKey(root))
                {
                    sizes[root] = 0;
                }
                sizes[root]++;
            }
            return sizes;
        }
    }
    
    private record Point3D(int X, int Y, int Z);
    
    private record Edge(int From, int To, double Distance) : IComparable<Edge>
    {
        public int CompareTo(Edge? other)
        {
            if (other == null) return 1;
            return Distance.CompareTo(other.Distance);
        }
    }
    
    private double DistanceSquared(Point3D a, Point3D b)
    {
        long dx = a.X - b.X;
        long dy = a.Y - b.Y;
        long dz = a.Z - b.Z;
        return dx * dx + dy * dy + dz * dz;
    }
    
    public override void SolvePart1(string input)
    {
        var lines = input.Split('\n', StringSplitOptions.RemoveEmptyEntries);
        var points = new List<Point3D>();
        
        // Parse points
        foreach (var line in lines)
        {
            var parts = line.Trim().Split(',');
            if (parts.Length == 3)
            {
                points.Add(new Point3D(
                    int.Parse(parts[0]),
                    int.Parse(parts[1]),
                    int.Parse(parts[2])
                ));
            }
        }
        
        // Generate all edges with distances
        var edges = new List<Edge>();
        for (int i = 0; i < points.Count; i++)
        {
            for (int j = i + 1; j < points.Count; j++)
            {
                double dist = DistanceSquared(points[i], points[j]);
                edges.Add(new Edge(i, j, dist));
            }
        }
        
        // Sort edges by distance
        edges.Sort();
        
        // Process the 1000 shortest connections (whether they succeed or not)
        var uf = new UnionFind(points.Count);
        int connectionsProcessed = 0;
        
        foreach (var edge in edges)
        {
            if (connectionsProcessed >= 1000)
            {
                break;
            }
            
            connectionsProcessed++;
            uf.Union(edge.From, edge.To);
        }
        
        // Get component sizes
        var componentSizes = uf.GetComponentSizes();
        var sizes = componentSizes.Values.OrderByDescending(x => x).ToList();
        
        // Multiply the three largest
        long result = (long)sizes[0] * sizes[1] * sizes[2];
        
        Console.WriteLine($"Day 8 Part 1: {result}");
    }
    
    public override void SolvePart2(string input)
    {
        var lines = input.Split('\n', StringSplitOptions.RemoveEmptyEntries);
        var points = new List<Point3D>();
        
        // Parse points
        foreach (var line in lines)
        {
            var parts = line.Trim().Split(',');
            if (parts.Length == 3)
            {
                points.Add(new Point3D(
                    int.Parse(parts[0]),
                    int.Parse(parts[1]),
                    int.Parse(parts[2])
                ));
            }
        }
        
        // Generate all edges with distances
        var edges = new List<Edge>();
        for (int i = 0; i < points.Count; i++)
        {
            for (int j = i + 1; j < points.Count; j++)
            {
                double dist = DistanceSquared(points[i], points[j]);
                edges.Add(new Edge(i, j, dist));
            }
        }
        
        // Sort edges by distance
        edges.Sort();
        
        // Keep connecting until all are in one circuit
        var uf = new UnionFind(points.Count);
        Edge? lastConnection = null;
        
        foreach (var edge in edges)
        {
            if (uf.Union(edge.From, edge.To))
            {
                lastConnection = edge;
                
                // Check if all are connected (only 1 component left)
                if (uf.GetComponentCount() == 1)
                {
                    break;
                }
            }
        }
        
        if (lastConnection != null)
        {
            int x1 = points[lastConnection.From].X;
            int x2 = points[lastConnection.To].X;
            long result = (long)x1 * x2;
            
            Console.WriteLine($"Day 8 Part 2: {result}");
        }
        else
        {
            Console.WriteLine($"Day 8 Part 2: ERROR - No connection found");
        }
    }
}