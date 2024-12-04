package aoc

import kotlin.test.Test
import kotlin.test.assertEquals

class Day03Test {
    private val day03 = Day03()

    private val testInput = """
        xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)do()?mul(8,5))
    """.trimIndent().lines()

    @Test
    fun testPart1() {
        val expected = 161 // 2*4 + 3*7 + 32*64 + 11*8 + 8*5
        assertEquals(expected, day03.part1(testInput), "Part 1 failed for test input")
    }

    @Test
    fun testPart2() {
        val expected = 48 // 2*4 + 8*5 (Only enabled mul after do/don't logic)
        assertEquals(expected, day03.part2(testInput), "Part 2 failed for test input")
    }

    @Test
    fun testPart1WithNoValidMul() {
        val input = listOf("random text without mul instructions")
        val expected = 0
        assertEquals(expected, day03.part1(input), "Part 1 failed for input with no valid mul instructions")
    }

    @Test
    fun testPart2WithNoEnabledMul() {
        val input = listOf("don't() mul(2,3) mul(4,5)")
        val expected = 0 // All multiplications are disabled
        assertEquals(expected, day03.part2(input), "Part 2 failed for input with no enabled mul instructions")
    }

    @Test
    fun testPart2WithMultipleSections() {
        val input = """
            do() mul(3,5) don't() mul(7,8) do() mul(2,4)
        """.trimIndent().lines()
        val expected = 15 + 8 // Only mul(3,5) and mul(2,4) are enabled
        assertEquals(expected, day03.part2(input), "Part 2 failed for input with multiple do/don't sections")
    }

    @Test
    fun testEdgeCaseWithEmptyInput() {
        val input = emptyList<String>()
        val expected = 0
        assertEquals(expected, day03.part1(input), "Part 1 failed for empty input")
        assertEquals(expected, day03.part2(input), "Part 2 failed for empty input")
    }
}
