package aoc

class Day09 {

    fun part1(input: String): Int {
        val sizes = input.split("").mapNotNull { it.toIntOrNull() }.toMutableList()
        val n = sizes.size

        // Compact the disk
        var readPtr = 0
        var writePtr = 0
        while (readPtr < n) {
            if (sizes[readPtr] > 0) {
                sizes[writePtr] = sizes[readPtr]
                writePtr++
            }
            readPtr++
        }

        // Calculate the checksum
        var checksum = 0
        var fileId = 0
        var pos = 0
        for (size in sizes) {
            if (size > 0) {
                checksum += pos * fileId
                fileId++
            }
            pos++
        }
        return checksum
    }

    fun part2(input: List<String>): Int {
        // TODO: Implement solution
        return 0
    }
}

fun main() {
    // test if implementation meets criteria from the description
    val testInput = """
        2333133121414131402
    """.trimIndent().lines()

    check(Day09().part1(testInput.toString()) == 1928) { "Part 1 test failed (actual: ${Day09().part1(testInput.toString())})" }
    check(Day09().part2(testInput) == 0) { "Part 2 test failed" }

    val input = Utils.readInput(9)
    println("Part 1: ${Day09().part1(input.toString())}")
    println("Part 2: ${Day09().part2(input)}")
}
