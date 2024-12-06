package aoc

import kotlin.test.Test
import kotlin.test.assertEquals

class Day06Test {

    private val sampleInput = """
        ....#.....
        .........#
        ..........
        ..#.......
        .......#..
        ..........
        .#..^.....
        ........#.
        #.........
        ......#...
    """.trimIndent().lines()

    private val minimalInput = """
        ..^..
        .....
        .....
        .....
        .....
    """.trimIndent().lines()

    private val emptyGrid = """
        .....
        .....
        .....
        .....
        .....
    """.trimIndent().lines()

    private val wallOnlyGrid = """
        #####
        #####
        #####
        #####
        #####
    """.trimIndent().lines()

    private val tinyGrid = """
        ^..
        ...
        ..#
    """.trimIndent().lines()

    @Test
    fun testPart1Sample() {
        val result = Day06().part1(sampleInput)
        assertEquals(41, result, "Part 1 failed for the sample input.")
    }

    @Test
    fun testPart2Sample() {
        val result = Day06().part2(sampleInput)
        assertEquals(6, result, "Part 2 failed for the sample input.")
    }

    @Test
    fun testPart2EmptyGrid() {
        val result = Day06().part2(emptyGrid)
        assertEquals(0, result, "Part 2 failed for the empty grid.")
    }

    @Test
    fun testPart2WallOnlyGrid() {
        val result = Day06().part2(wallOnlyGrid)
        assertEquals(0, result, "Part 2 failed for the wall-only grid.")
    }
}
