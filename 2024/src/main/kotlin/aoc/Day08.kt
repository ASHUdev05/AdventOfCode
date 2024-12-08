package aoc

class Day08 {

    data class Antenna(val x: Int, val y: Int, val frequency: Char)

    fun part1(input: List<String>): Int {
        val antennas = parseAntennas(input)
        val antinodes = mutableSetOf<Pair<Int, Int>>()

        // Compute antinodes for each frequency
        for (i in antennas.indices) {
            for (j in i + 1 until antennas.size) {
                val a1 = antennas[i]
                val a2 = antennas[j]
                if (a1.frequency != a2.frequency) continue

                // Calculate all potential antinodes between a1 and a2
                val dx = a2.x - a1.x
                val dy = a2.y - a1.y

                // Antinode on one side of a1 and a2
                val cx = a1.x - dx
                val cy = a1.y - dy
                if (isWithinBounds(cx, cy, input)) antinodes.add(Pair(cx, cy))

                // Antinode on the opposite side of a1 and a2
                val ex = a2.x + dx
                val ey = a2.y + dy
                if (isWithinBounds(ex, ey, input)) antinodes.add(Pair(ex, ey))
            }
        }

        return antinodes.size
    }

    fun part2(input: List<String>): Int {
        val antennas = parseAntennas(input)
        val antinodes = mutableSetOf<Pair<Int, Int>>()

        // Group antennas by frequency
        val frequencyGroups = antennas.groupBy { it.frequency }

        for ((_, group) in frequencyGroups) {
            if (group.size < 2) continue

            // Add each antenna position as an antinode
            group.forEach { antinodes.add(Pair(it.x, it.y)) }

            // Check all pairs of antennas in the same group
            for (i in group.indices) {
                for (j in i + 1 until group.size) {
                    val a1 = group[i]
                    val a2 = group[j]

                    // Calculate all positions in line with a1 and a2
                    val dx = a2.x - a1.x
                    val dy = a2.y - a1.y

                    // Check all potential antinodes along the line
                    for (k in -100..100) { // Arbitrary range to ensure all positions are covered
                        val nx = a1.x + k * dx
                        val ny = a1.y + k * dy
                        if (isWithinBounds(nx, ny, input)) antinodes.add(Pair(nx, ny))
                    }
                }
            }
        }

        return antinodes.size
    }

    private fun parseAntennas(input: List<String>): List<Antenna> {
        val antennas = mutableListOf<Antenna>()
        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                if (char.isLetterOrDigit()) antennas.add(Antenna(x, y, char))
            }
        }
        return antennas
    }

    private fun isWithinBounds(x: Int, y: Int, map: List<String>): Boolean {
        return x in 0 until map[0].length && y in map.indices
    }
}

fun main() {
    val testInput = """
        ............
        ........0...
        .....0......
        .......0....
        ....0.......
        ......A.....
        ............
        ............
        ........A...
        .........A..
        ............
        ............
    """.trimIndent().lines()

    check(Day08().part1(testInput) == 14) { "Part 1 test failed" }
    check(Day08().part2(testInput) == 34) { "Part 2 test failed" }

    val input = Utils.readInput(8)
    println("Part 1: ${Day08().part1(input)}")
    println("Part 2: ${Day08().part2(input)}")
}
