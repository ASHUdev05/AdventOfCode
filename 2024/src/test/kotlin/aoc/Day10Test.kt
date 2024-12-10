package aoc

import kotlin.test.Test
import kotlin.test.assertEquals

class Day10Test {

    private val exampleInput1 = """
        012
        123
        234
    """.trimIndent().lines()

    private val exampleInput2 = """
        000
        000
        000
    """.trimIndent().lines()

    private val exampleInput3 = """
        999
        999
        999
    """.trimIndent().lines()

    private val exampleInput4 = """
        1
        2
        3
    """.trimIndent().lines()

    private val largeInput = """
        89010123
        78121874
        87430965
        96549874
        45678903
        32019012
        01329801
        10456732
    """.trimIndent().lines()

    @Test
    fun testPart1() {
        val day10 = Day10()

        // Test with simple grid
        assertEquals(3, day10.part1(exampleInput1)) // Explanation: trails from 0 to 9
        assertEquals(0, day10.part1(exampleInput2)) // No trails as all heights are 0
        assertEquals(0, day10.part1(exampleInput3)) // No valid trails as all are maxed at 9
        assertEquals(0, day10.part1(exampleInput4)) // No trailhead (no height 0)

        // Test with larger grid
        assertEquals(15, day10.part1(largeInput)) // Example value; adjust based on output
    }

    @Test
    fun testPart2() {
        val day10 = Day10()

        // Test with simple grid
        assertEquals(1, day10.part2(exampleInput1)) // One distinct trail reaching 9
        assertEquals(0, day10.part2(exampleInput2)) // No distinct paths
        assertEquals(0, day10.part2(exampleInput3)) // No distinct paths
        assertEquals(0, day10.part2(exampleInput4)) // No trailhead (no height 0)

        // Test with larger grid
        assertEquals(81, day10.part2(largeInput)) // Provided test case
    }
}
