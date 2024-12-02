package aoc

class Day02 {
    private fun isReportSafe(numbers: List<Int>): Boolean {
        if (numbers.size < 2) return true

        // Check if numbers are strictly increasing or decreasing
        var increasing: Boolean? = null

        for (i in 0..(numbers.size - 2)) {
            val diff = numbers[i + 1] - numbers[i]

            // Check if difference is between 1 and 3 (inclusive)
            if (diff == 0 || diff > 3 || diff < -3) {
                return false
            }

            // Determine if sequence is increasing or decreasing
            val currentIncreasing = diff > 0

            if (increasing == null) {
                increasing = currentIncreasing
            } else if (increasing != currentIncreasing) {
                return false
            }
        }

        return true
    }

    private fun canBeMadeSafe(numbers: List<Int>): Boolean {
        // If it's already safe, no need to remove anything
        if (isReportSafe(numbers)) return true

        // Try removing each number one at a time
        for (i in numbers.indices) {
            val withoutOne = numbers.filterIndexed { index, _ -> index != i }
            if (isReportSafe(withoutOne)) return true
        }

        return false
    }

    fun part1(input: List<String>): Int {
        return input
            .filter { it.isNotBlank() }
            .map { line ->
                line.trim().split("\\s+".toRegex()).map { it.toInt() }
            }
            .count { isReportSafe(it) }
    }

    fun part2(input: List<String>): Int {
        return input
            .filter { it.isNotBlank() }
            .map { line ->
                line.trim().split("\\s+".toRegex()).map { it.toInt() }
            }
            .count { canBeMadeSafe(it) }
    }
}

fun main() {
    // Test if implementation meets criteria from the description
    val testInput = """
        7 6 4 2 1
        1 2 7 8 9
        9 7 6 2 1
        1 3 2 4 5
        8 6 4 4 1
        1 3 6 7 9
    """.trimIndent().lines()

    check(Day02().part1(testInput) == 2) { "Part 1 test failed" }
    check(Day02().part2(testInput) == 4) { "Part 2 test failed" }

    val input = Utils.readInput(2)
    println("Part 1: ${Day02().part1(input)}")
    println("Part 2: ${Day02().part2(input)}")
}