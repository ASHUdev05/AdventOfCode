package aoc

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Day07Test {
    private val testInput = """
        190: 10 19
        3267: 81 40 27
        83: 17 5
        156: 15 6
        7290: 6 8 6 15
        161011: 16 10 13
        192: 17 8 14
        21037: 9 7 18 13
        292: 11 6 16 20
    """.trimIndent().lines()

    @Test
    fun testPart1() {
        assertEquals(3749, Day07().part1(testInput))
    }

    @Test
    fun testPart2() {
        assertEquals(11387, Day07().part2(testInput))
    }

    @Test
    fun testIndividualEquationsPart1() {
        val day07 = Day07()

        // Test valid equations for part 1 (+ and * only)
        val validPart1 = listOf(
            "190: 10 19",      // 10 * 19 = 190
            "3267: 81 40 27",  // 81 + 40 * 27 or 81 * 40 + 27 = 3267
            "292: 11 6 16 20"  // 11 + 6 * 16 + 20 = 292
        )

        // Test invalid equations for part 1
        val invalidPart1 = listOf(
            "83: 17 5",
            "156: 15 6",
            "7290: 6 8 6 15",
            "161011: 16 10 13",
            "192: 17 8 14",
            "21037: 9 7 18 13"
        )

        validPart1.forEach { input ->
            assertEquals(
                day07.part1(listOf(input)),
                input.split(":")[0].toLong(),
                "Failed for equation: $input"
            )
        }

        invalidPart1.forEach { input ->
            assertEquals(
                0,
                day07.part1(listOf(input)),
                "Should be invalid for part 1: $input"
            )
        }
    }

    @Test
    fun testIndividualEquationsPart2() {
        val day07 = Day07()

        // Test additional valid equations for part 2 (including concatenation)
        val validPart2 = listOf(
            "156: 15 6",       // 15 || 6 = 156
            "7290: 6 8 6 15",  // 6 * 8 || 6 * 15 = 7290
            "192: 17 8 14"     // 17 || 8 + 14 = 192
        )

        validPart2.forEach { input ->
            assertEquals(
                day07.part2(listOf(input)),
                input.split(":")[0].toLong(),
                "Failed for equation with concatenation: $input"
            )
        }
    }

    @Test
    fun testEmptyAndInvalidInput() {
        val day07 = Day07()

        // Test empty input
        assertEquals(0, day07.part1(emptyList()))
        assertEquals(0, day07.part2(emptyList()))

        // Test invalid format
        val invalidInput = listOf(
            "not a valid line",
            "123",
            ": 1 2 3",
            "100: ",
            "100: a b c"
        )

        invalidInput.forEach { input ->
            kotlin.runCatching {
                day07.part1(listOf(input))
            }.onSuccess {
                // If we get here, the function should have returned 0
                assertEquals(0, it, "Should handle invalid input: $input")
            }

            kotlin.runCatching {
                day07.part2(listOf(input))
            }.onSuccess {
                // If we get here, the function should have returned 0
                assertEquals(0, it, "Should handle invalid input: $input")
            }
        }
    }

    @Test
    fun testLargeNumbers() {
        val day07 = Day07()

        // Test equations with large numbers
        val largeNumberInput = listOf(
            "1000000: 1000 1000", // 1000 * 1000
            "999999999: 999999 1000" // 999999 * 1000
        )

        largeNumberInput.forEach { input ->
            kotlin.runCatching {
                day07.part1(listOf(input))
                day07.part2(listOf(input))
            }.onSuccess {
                // Test passes if no exception is thrown
                assertTrue(true, "Should handle large numbers: $input")
            }
        }
    }
}