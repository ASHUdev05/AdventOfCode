# --- Configuration ---
# Uses the current folder name for the Solution name, or defaults to "AoC2025"
$CurrentDirName = Split-Path -Path (Get-Location) -Leaf
$SolutionFileName = if ($CurrentDirName -match "^\d+$") { "AoC$CurrentDirName" } else { $CurrentDirName }
$ProjectName = "Solutions"
$DotNetVersion = "net10.0"

Write-Host "Setting up AoC in current directory: $(Get-Location)" -ForegroundColor Cyan

# --- 1. Scaffold Solution and Project ---
# Create the .sln file in the CURRENT folder
if (-not (Test-Path "$SolutionFileName.sln")) {
    dotnet new sln -n $SolutionFileName
}

# Create the Project in a subfolder (to keep bin/obj separate from root)
if (-not (Test-Path $ProjectName)) {
    dotnet new console -n $ProjectName -f $DotNetVersion
    dotnet sln add "$ProjectName/$ProjectName.csproj"
} else {
    Write-Host "Project folder '$ProjectName' already exists. Skipping creation." -ForegroundColor Yellow
}

# Create Core directory
New-Item -ItemType Directory -Force -Path "$ProjectName/Core" | Out-Null

# --- 2. Create BaseDay.cs (The Engine) ---
Write-Host "Writing BaseDay.cs..." -ForegroundColor Cyan
$BaseDayContent = @"
using System.Net;

namespace $ProjectName.Core;

public abstract class BaseDay
{
    private const string InputFolderPath = "Inputs";
    
    // TODO: PASTE YOUR SESSION COOKIE HERE
    private const string SessionCookie = "Paste_Your_Session_Cookie_Value_Here";

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
            client.DefaultRequestHeaders.UserAgent.ParseAdd("github.com/YourUsername/AoC2025 by user@email.com");

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
"@
Set-Content -Path "$ProjectName/Core/BaseDay.cs" -Value $BaseDayContent

# --- 3. Create Program.cs (The Runner) ---
Write-Host "Writing Program.cs..." -ForegroundColor Cyan
$ProgramContent = @"
using System.Reflection;
using $ProjectName.Core;

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
"@
Set-Content -Path "$ProjectName/Program.cs" -Value $ProgramContent

# --- 4. Generate Days 01-12 ---
Write-Host "Generating Day 01 to Day 12..." -ForegroundColor Cyan

# Create a 'Days' folder to keep it tidy
New-Item -ItemType Directory -Force -Path "$ProjectName/Days" | Out-Null

1..12 | ForEach-Object {
    $dayNum = $_
    $dayStr = "{0:D2}" -f $dayNum
    $fileName = "$ProjectName/Days/Day$dayStr.cs"

    if (-not (Test-Path $fileName)) {
        $dayContent = @"
using $ProjectName.Core;

namespace $ProjectName.Days;

public class Day$dayStr : BaseDay
{
    public override void SolvePart1(string input)
    {
        var lines = input.Split('\n');
        Console.WriteLine($"Day $dayNum Part 1 [Not Implemented]");
    }

    public override void SolvePart2(string input)
    {
        Console.WriteLine($"Day $dayNum Part 2 [Not Implemented]");
    }
}
"@
        Set-Content -Path $fileName -Value $dayContent
    }
}

# --- 5. Update .gitignore ---
Write-Host "Creating .gitignore in root..." -ForegroundColor Cyan
$GitIgnore = @"
**/bin/
**/obj/
.vs/
**/Inputs/
.idea/
.DS_Store
"@
Set-Content -Path ".gitignore" -Value $GitIgnore

Write-Host "--- Setup Complete in $(Get-Location) ---" -ForegroundColor Yellow
Write-Host "1. Edit '$ProjectName/Core/BaseDay.cs' to add your Session Cookie."
Write-Host "2. Run 'dotnet run --project $ProjectName' to start."