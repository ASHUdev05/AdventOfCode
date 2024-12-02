package aoc

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Day02Test {
    private val testInput = """
        7 6 4 2 1
        1 2 7 8 9
        9 7 6 2 1
        1 3 2 4 5
        8 6 4 4 1
        1 3 6 7 9
    """.trimIndent().lines()

    private val day02 = Day02()

    @Test
    fun testPart1() {
        assertEquals(2, day02.part1(testInput))
    }

    @Test
    fun testPart2() {
        assertEquals(4, day02.part2(testInput))
    }

    @Test
    fun testIsReportSafe() {
        val tests = listOf(
            "7 6 4 2 1" to true,      // Decreasing by 1 or 2
            "1 2 7 8 9" to false,     // Jump of 5 between 2 and 7
            "9 7 6 2 1" to false,     // Jump of 4 between 6 and 2
            "1 3 2 4 5" to false,     // Mixed increasing/decreasing
            "8 6 4 4 1" to false,     // Repeated number 4
            "1 3 6 7 9" to true       // All increasing by 1-3
        )

        tests.forEach { (input, expected) ->
            val numbers = input.split(" ").map { it.toInt() }
            assertEquals(
                expected,
                Day02::class.java.getDeclaredMethod("isReportSafe", List::class.java)
                    .apply { isAccessible = true }
                    .invoke(day02, numbers) as Boolean,
                "Failed for input: $input"
            )
        }
    }

    @Test
    fun testCanBeMadeSafe() {
        val tests = listOf(
            "7 6 4 2 1" to true,      // Already safe
            "1 2 7 8 9" to false,     // Can't be made safe
            "9 7 6 2 1" to false,     // Can't be made safe
            "1 3 2 4 5" to true,      // Safe by removing 3
            "8 6 4 4 1" to true,      // Safe by removing one 4
            "1 3 6 7 9" to true       // Already safe
        )

        tests.forEach { (input, expected) ->
            val numbers = input.split(" ").map { it.toInt() }
            assertEquals(
                expected,
                Day02::class.java.getDeclaredMethod("canBeMadeSafe", List::class.java)
                    .apply { isAccessible = true }
                    .invoke(day02, numbers) as Boolean,
                "Failed for input: $input"
            )
        }
    }

    @Test
    fun testEdgeCases() {
        val method = Day02::class.java.getDeclaredMethod("isReportSafe", List::class.java)
            .apply { isAccessible = true }

        // Empty list
        assertTrue(method.invoke(day02, emptyList<Int>()) as Boolean)

        // Single number
        assertTrue(method.invoke(day02, listOf(1)) as Boolean)

        // Two numbers at boundary conditions
        assertTrue(method.invoke(day02, listOf(1, 4)) as Boolean)  // Difference of 3
        assertTrue(method.invoke(day02, listOf(4, 1)) as Boolean)  // Difference of -3
        assertFalse(method.invoke(day02, listOf(1, 5)) as Boolean)  // Difference of 4
        assertFalse(method.invoke(day02, listOf(5, 1)) as Boolean)  // Difference of -4
    }
}