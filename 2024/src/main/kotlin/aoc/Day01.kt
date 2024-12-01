package aoc

class Day01 {

    fun part1(input: List<String>): Int {
        // Parse the input into two separate lists, skipping empty lines
        val leftList = mutableListOf<Int>()
        val rightList = mutableListOf<Int>()

        for (line in input.filter { it.isNotBlank() }) { // Skip empty lines
            val (left, right) = line.split("   ").map { it.toInt() }
            leftList.add(left)
            rightList.add(right)
        }

        // Sort both lists
        leftList.sort()
        rightList.sort()

        // Calculate the total distance between paired elements
        return leftList.zip(rightList).sumOf { (left, right) -> kotlin.math.abs(left - right) }
    }

    fun part2(input: List<String>): Int {
        // Parse the input into two separate lists
        val leftList = mutableListOf<Int>()
        val rightList = mutableListOf<Int>()

        for (line in input) {
            if (line.isBlank()) continue // Skip blank lines
            val parts = line.trim().split("\\s+".toRegex()) // Split by any whitespace
            if (parts.size != 2) continue // Skip malformed lines

            try {
                val (left, right) = parts.map { it.toInt() }
                leftList.add(left)
                rightList.add(right)
            } catch (e: NumberFormatException) {
                continue // Skip invalid lines
            }
        }

        // Count occurrences in the right list
        val rightFrequency = rightList.groupingBy { it }.eachCount()

        // Calculate the similarity score
        return leftList.sumOf { leftNumber ->
            val countInRight = rightFrequency[leftNumber] ?: 0
            leftNumber * countInRight
        }
    }
}

fun main() {
    // test if implementation meets criteria from the description
    val testInput = """
    3   4
    4   3
    2   5
    1   3
    3   9
    3   3
""".trimIndent().lines()

    check(Day01().part1(testInput) == 11) { "Part 1 test failed" }
    check(Day01().part2(testInput) == 31) { "Part 2 test failed" }

    val input = Utils.readInput(1)
    println("Part 1: ${Day01().part1(input)}")
    println("Part 2: ${Day01().part2(input)}")
}
