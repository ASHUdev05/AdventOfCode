package aoc

class Day07 {
    // Parses a line into test value and numbers
    private fun parseLine(line: String): Pair<Long, List<Long>> {
        val parts = line.split(": ")
        val testValue = parts[0].toLong()
        val numbers = parts[1].split(" ").map { it.toLong() }
        return testValue to numbers
    }

    // Helper function to concatenate two numbers
    private fun concatenate(a: Long, b: Long): Long {
        return "$a$b".toLong()
    }

    // Evaluates expression left-to-right with given operators
    private fun evaluate(numbers: List<Long>, operators: List<String>): Long {
        var result = numbers[0]
        for (i in operators.indices) {
            when (operators[i]) {
                "+" -> result += numbers[i + 1]
                "*" -> result *= numbers[i + 1]
                "||" -> result = concatenate(result, numbers[i + 1])
            }
        }
        return result
    }

    // Generates operator combinations for part 1 (+ and * only)
    private fun generateOperatorsPart1(count: Int): List<List<String>> {
        val operators = listOf("+", "*")
        val result = mutableListOf<List<String>>()

        fun generate(current: List<String>) {
            if (current.size == count) {
                result.add(current)
                return
            }
            for (op in operators) {
                generate(current + op)
            }
        }

        generate(emptyList())
        return result
    }

    // Generates operator combinations for part 2 (including ||)
    private fun generateOperatorsPart2(count: Int): List<List<String>> {
        val operators = listOf("+", "*", "||")
        val result = mutableListOf<List<String>>()

        fun generate(current: List<String>) {
            if (current.size == count) {
                result.add(current)
                return
            }
            for (op in operators) {
                generate(current + op)
            }
        }

        generate(emptyList())
        return result
    }

    // Checks if equation can be satisfied with given operator combinations
    private fun canSatisfyEquation(testValue: Long, numbers: List<Long>, operators: List<List<String>>): Boolean {
        return operators.any { operatorList ->
            try {
                evaluate(numbers, operatorList) == testValue
            } catch (e: NumberFormatException) {
                false
            }
        }
    }

    fun part1(input: List<String>): Long {
        return input
            .filter { it.isNotBlank() }
            .map { parseLine(it) }
            .filter { (testValue, numbers) ->
                val operatorCount = numbers.size - 1
                val possibleOperators = generateOperatorsPart1(operatorCount)
                canSatisfyEquation(testValue, numbers, possibleOperators)
            }
            .sumOf { it.first }
    }

    fun part2(input: List<String>): Long {
        return input
            .filter { it.isNotBlank() }
            .map { parseLine(it) }
            .filter { (testValue, numbers) ->
                val operatorCount = numbers.size - 1
                val possibleOperators = generateOperatorsPart2(operatorCount)
                canSatisfyEquation(testValue, numbers, possibleOperators)
            }
            .sumOf { it.first }
    }
}

fun main() {
    val testInput = """
        190: 10 19
        3267: 81 40 27
        83: 17 5
        156: 15 6
        7290: 6 8 6 15
        161011: 16 10 13
        192: 17 8 14
        21037: 9 7 18 13
        292: 11 6 16 20
    """.trimIndent().lines()

    check(Day07().part1(testInput) == 3749L) { "Part 1 test failed" }
    check(Day07().part2(testInput) == 11387L) { "Part 2 test failed" }

    val input = Utils.readInput(7)
    println("Part 1: ${Day07().part1(input)}")
    println("Part 2: ${Day07().part2(input)}")
}