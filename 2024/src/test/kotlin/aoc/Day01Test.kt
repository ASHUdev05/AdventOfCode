package aoc

import kotlin.test.Test
import kotlin.test.assertEquals

class Day01Test {

    @Test
    fun testPart1_ExampleInput() {
        val input = """
            3   4
            4   3
            2   5
            1   3
            3   9
            3   3
        """.trimIndent().lines()
        val expected = 11
        val actual = Day01().part1(input)
        assertEquals(expected, actual)
    }

    @Test
    fun testPart1_EmptyInput() {
        val input = emptyList<String>()
        val expected = 0
        val actual = Day01().part1(input)
        assertEquals(expected, actual)
    }

    // Add more test cases for part 1 with different input scenarios

    @Test
    fun testPart2_ExampleInput() {
        val input = """
            3   4
            4   3
            2   5
            1   3
            3   9
            3   3
        """.trimIndent().lines()
        val expected = 31
        val actual = Day01().part2(input)
        assertEquals(expected, actual)
    }

    @Test
    fun testPart2_SingleElementInput() {
        val input = listOf("100")
        val expected = 0
        val actual = Day01().part2(input)
        assertEquals(expected, actual)
    }
}