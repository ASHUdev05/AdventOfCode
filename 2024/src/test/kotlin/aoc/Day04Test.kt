package aoc

import kotlin.test.Test
import kotlin.test.assertEquals

class Day04Test {
    private val testInput = """
        MMMSXXMASM
        MSAMXMSMSA
        AMXSXMAAMM
        MSAMASMSMX
        XMASAMXAMM
        XXAMMXXAMA
        SMSMSASXSS
        SAXAMASAAA
        MAMMMXMMMM
        MXMXAXMASX
    """.trimIndent().lines()

    @Test
    fun testPart1() {
        assertEquals(18, Day04().part1(testInput))
    }

    @Test
    fun testPart2() {
        assertEquals(9, Day04().part2(testInput))
    }

    // Additional test cases with simpler patterns
    @Test
    fun testPart1Simple() {
        val simpleInput = """
            XMAS
            ....
            ....
            ....
        """.trimIndent().lines()
        assertEquals(1, Day04().part1(simpleInput))
    }

    @Test
    fun testPart1Diagonal() {
        val diagonalInput = """
            X...
            .M..
            ..A.
            ...S
        """.trimIndent().lines()
        assertEquals(1, Day04().part1(diagonalInput))
    }

    @Test
    fun testPart1Backwards() {
        val backwardsInput = """
            SAMX
            ....
            ....
            ....
        """.trimIndent().lines()
        assertEquals(1, Day04().part1(backwardsInput))
    }

    @Test
    fun testPart2Simple() {
        val simpleXInput = """
            M.S
            .A.
            M.S
        """.trimIndent().lines()
        assertEquals(1, Day04().part2(simpleXInput))
    }

    @Test
    fun testPart2Backwards() {
        val backwardsXInput = """
            S.M
            .A.
            S.M
        """.trimIndent().lines()
        assertEquals(1, Day04().part2(backwardsXInput))
    }
}