using System.Reflection;
using Solutions.Core;

var dayTypes = Assembly.GetExecutingAssembly()
    .GetTypes()
    .Where(t => t.IsSubclassOf(typeof(BaseDay)) && !t.IsAbstract)
    .OrderBy(t => t.Name)
    .ToList();

if (dayTypes.Count == 0)
{
    Console.WriteLine("No solutions found.");
    return;
}

// Check if a specific day was passed as an argument (e.g., dotnet run 5)
BaseDay instance = null;
if (args.Length > 0 && int.TryParse(args[0], out int selectedDay))
{
    var targetType = dayTypes.FirstOrDefault(t => t.Name == $"Day{selectedDay:D2}");
    if (targetType != null) instance = (BaseDay)Activator.CreateInstance(targetType)!;
    else Console.WriteLine($"Day {selectedDay} not found.");
}
else
{
    // Default: Run the latest day
    instance = (BaseDay)Activator.CreateInstance(dayTypes.Last())!;
}

instance?.Run();
