using System.Net;

namespace Solutions.Core;

public abstract class BaseDay
{
    private const string InputFolderPath = "Inputs";
    
    // TODO: PASTE YOUR SESSION COOKIE HERE
    private const string SessionCookie = "53616c7465645f5f85520f33ad3dcd4d294ebbad0236555d7199c1eb79adce02f325c2e7c4a1c60342594d96e15d4edd923853a2cfbff9b51c3e12d6621008c5";

    public abstract void SolvePart1(string input);
    public abstract void SolvePart2(string input);

    public void Run()
    {
        var dayName = this.GetType().Name; 
        var dayNumber = int.Parse(dayName.Substring(3));
        
        Console.WriteLine($"--- {dayName} ---");
        
        var input = GetInput(dayNumber);
        
        Console.WriteLine("Part 1:");
        SolvePart1(input);
        
        Console.WriteLine("Part 2:");
        SolvePart2(input);
    }

    private string GetInput(int day)
    {
        // Save inputs in the project root/Inputs
        if (!Directory.Exists(InputFolderPath)) Directory.CreateDirectory(InputFolderPath);

        string filePath = Path.Combine(InputFolderPath, $"{day:D2}.txt");

        if (!File.Exists(filePath))
        {
            if (SessionCookie == "Paste_Your_Session_Cookie_Value_Here" || string.IsNullOrEmpty(SessionCookie))
            {
                Console.WriteLine("ERROR: Session Cookie not set in BaseDay.cs. Cannot download input.");
                return "";
            }

            Console.WriteLine($"Downloading input for Day {day}...");
            using var client = new HttpClient();
            client.DefaultRequestHeaders.Add("Cookie", $"session={SessionCookie}");
            client.DefaultRequestHeaders.TryAddWithoutValidation("User-Agent", "github.com/ASHUdev05/AoC2025 by amphoteric629@gmail.com");

            string url = $"https://adventofcode.com/2025/day/{day}/input";
            try 
            {
                string inputData = client.GetStringAsync(url).Result.TrimEnd();
                File.WriteAllText(filePath, inputData);
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error downloading input: {ex.Message}");
                return "";
            }
        }

        return File.ReadAllText(filePath);
    }
}
