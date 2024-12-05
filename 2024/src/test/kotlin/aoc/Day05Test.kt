package aoc

import kotlin.test.Test
import kotlin.test.assertEquals

class Day05Test {
    private val testInput = """
        47|53
        97|13
        97|61
        97|47
        75|29
        61|13
        75|53
        29|13
        97|29
        53|29
        61|53
        97|53
        61|29
        47|13
        75|47
        97|75
        47|61
        75|61
        47|29
        75|13
        53|13

        75,47,61,53,29
        97,61,53,29,13
        75,29,13
        75,97,47,61,53
        61,13,29
        97,13,75,29,47
    """.trimIndent().lines()

    @Test
    fun testPart1() {
        assertEquals(143, Day05().part1(testInput))
    }

    @Test
    fun testPart1WithSimpleInput() {
        val simpleInput = """
            1|2
            2|3
            
            1,2,3
            3,2,1
        """.trimIndent().lines()

        assertEquals(2, Day05().part1(simpleInput))
    }

    @Test
    fun testPart1WithSingleRule() {
        val input = """
            1|2
            
            1,2
            2,1
        """.trimIndent().lines()

        assertEquals(2, Day05().part1(input))
    }

    @Test
    fun testPart2() {
        assertEquals(123, Day05().part2(testInput))
    }

    @Test
    fun testPart2WithSimpleInput() {
        val simpleInput = """
            1|2
            2|3
            
            3,1,2
            2,1,3
        """.trimIndent().lines()

        assertEquals(4, Day05().part2(simpleInput))  // 2 + 2 (middle numbers after sorting to 1,2,3 and 1,2,3)
    }

    @Test
    fun testPart2WithSingleSequence() {
        val input = """
            1|2
            2|3
            1|3
            
            3,2,1
        """.trimIndent().lines()

        assertEquals(2, Day05().part2(input))  // Middle number after sorting to 1,2,3
    }

    @Test
    fun testPart2WithComplexRules() {
        val input = """
            5|1
            4|1
            3|1
            2|1
            5|2
            4|2
            3|2
            5|3
            4|3
            5|4
            
            1,2,3,4,5
            5,4,3,2,1
        """.trimIndent().lines()

        assertEquals(3, Day05().part2(input))  // Only second sequence needs sorting, middle number is 3
    }
}