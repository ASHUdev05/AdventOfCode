package aoc

class Day03 {
    fun part1(input: List<String>): Int {
        val mulRegex = Regex("""mul\((\d+),(\d+)\)""")
        return input.sumOf { line ->
            mulRegex.findAll(line)
                .sumOf { mr -> mr.groupValues[1].toInt() * mr.groupValues[2].toInt() }
        }
    }

    fun part2(input: List<String>): Int {
        val regex = Regex("""(?s)don't\(\).*?(?:do\(\)|$)|mul\((\d+),(\d+)\)""")
        return input.sumOf { line ->
            regex.findAll(line)
                .sumOf { mr -> (mr.groupValues[1].toIntOrNull() ?: 0) * (mr.groupValues[2].toIntOrNull() ?: 0) }
        }
    }
}

fun main() {
    val testInput = """
        xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)do()?mul(8,5))
    """.trimIndent().lines()

    val day03 = Day03()
    check(day03.part1(testInput) == 161) { "Part 1 test failed" }
    check(day03.part2(testInput) == 48) { "Part 2 test failed" }

    val input = Utils.readInputAsGroups(3)
    println("Part 1: ${day03.part1(input)}")
    println("Part 2: ${day03.part2(input)}")
}