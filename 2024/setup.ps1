# setup.ps1

param(
    [Parameter(Mandatory=$true)]
    [int]$Day,
    [Parameter(Mandatory=$false)]
    [int]$Year = 2024
)

# Config handling
$ConfigPath = "aoc_config.json"
$SessionCookie = ""

# Check for existing config or create new one
if (Test-Path $ConfigPath) {
    $Config = Get-Content $ConfigPath | ConvertFrom-Json
    $SessionCookie = $Config.session_cookie
} else {
    $SessionCookie = Read-Host "Please enter your Advent of Code session cookie"
    @{
        session_cookie = $SessionCookie
    } | ConvertTo-Json | Out-File $ConfigPath
    Write-Host "Session cookie saved to $ConfigPath"
}

if ([string]::IsNullOrEmpty($SessionCookie)) {
    Write-Host "No session cookie found. Please provide one."
    $SessionCookie = Read-Host "Enter your Advent of Code session cookie"
    @{
        session_cookie = $SessionCookie
    } | ConvertTo-Json | Out-File $ConfigPath
}

$DayPadded = $Day.ToString("00")

# Create directories if they don't exist
New-Item -ItemType Directory -Force -Path "src/main/kotlin/aoc" | Out-Null
New-Item -ItemType Directory -Force -Path "src/main/resources" | Out-Null
New-Item -ItemType Directory -Force -Path "src/test/kotlin/aoc" | Out-Null

# Download input file using curl
$InputPath = "src/main/resources/day$DayPadded.txt"
if (-not (Test-Path $InputPath)) {
    try {
        Write-Host "Attempting to download input file for Day $DayPadded..."
        $Url = "https://adventofcode.com/$Year/day/$Day/input"
        $CurlCommand = "curl.exe '$Url' -H 'Cookie: session=$SessionCookie' --output '$InputPath'"
        
        Invoke-Expression $CurlCommand
        
        if (Test-Path $InputPath) {
            $Content = Get-Content $InputPath
            if ($Content -match "Puzzle inputs differ by user") {
                Remove-Item $InputPath
                throw "Invalid session cookie"
            }
            Write-Host "Successfully downloaded input for Day $DayPadded"
        } else {
            throw "Failed to create input file"
        }
    }
    catch {
        Write-Host "Error downloading input: $_"
        Write-Host "Created empty input file instead. Please paste input manually."
        New-Item -ItemType File -Force -Path $InputPath | Out-Null
    }
}

# Main solution file content
$MainContent = @"
package aoc

class Day$DayPadded {

    fun part1(input: List<String>): Int {
        // TODO: Implement solution
        return 0
    }

    fun part2(input: List<String>): Int {
        // TODO: Implement solution
        return 0
    }
}

fun main() {
    // test if implementation meets criteria from the description
    val testInput = """
        test data here
    """.trimIndent().lines()

    check(Day$DayPadded().part1(testInput) == 0) { "Part 1 test failed" }
    check(Day$DayPadded().part2(testInput) == 0) { "Part 2 test failed" }

    val input = Utils.readInput($Day)
    println("Part 1: `${Day$DayPadded().part1(input)}")
    println("Part 2: `${Day$DayPadded().part2(input)}")
}
"@

# Test file content
$TestContent = @"
package aoc

import kotlin.test.Test
import kotlin.test.assertEquals

class Day${DayPadded}Test {
    private val testInput = """
        test data here
    """.trimIndent().lines()

    @Test
    fun testPart1() {
        // TODO: Add test cases
    }

    @Test
    fun testPart2() {
        // TODO: Add test cases
    }
}
"@

# Create the files
$MainContent | Out-File -FilePath "src/main/kotlin/aoc/Day$DayPadded.kt" -Encoding UTF8
$TestContent | Out-File -FilePath "src/test/kotlin/aoc/Day${DayPadded}Test.kt" -Encoding UTF8

Write-Host ("Created files for Day " + $DayPadded + ":")
Write-Host ("- src/main/kotlin/aoc/Day" + $DayPadded + ".kt")
Write-Host ("- src/test/kotlin/aoc/Day" + $DayPadded + "Test.kt")
Write-Host ("- src/main/resources/day" + $DayPadded + ".txt")