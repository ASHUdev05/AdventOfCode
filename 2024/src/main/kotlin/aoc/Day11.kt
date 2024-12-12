package aoc

class Day11 {

    fun part1(input: List<String>, blinks: Int): Int {
        val initialStones = input.first().split(" ")
            .mapNotNull { it.toIntOrNull() } // Safely parse integers, ignoring invalid ones

        var currentStones = initialStones
        repeat(blinks) {
            val res = mutableListOf<List<Int>>()
            for (num in currentStones) {
                res.add(rules(num))
            }
            currentStones = res.flatten()
        }

        return currentStones.size
    }

    fun part2(input: List<String>, blinks: Int): Int {
        val initialStones = input.first().split(" ")
            .mapNotNull { it.toIntOrNull() } // Safely parse integers, ignoring invalid ones
        val memo = mutableMapOf<Pair<Int, Int>, Int>()

        fun dfs(num: Int, i: Int): Int {
            if (i == 0) return 1

            val key = Pair(num, i)
            if (key in memo) return memo[key]!!

            val count = rules(num).sumOf { dfs(it, i - 1) }
            memo[key] = count
            return count
        }

        return initialStones.sumOf { dfs(it, blinks) }
    }

    private fun rules(number: Int): List<Int> {
        return when {
            number == 0 -> listOf(1)
            number.toString().length % 2 == 0 -> {
                val str = number.toString()
                val mid = str.length / 2
                listOf(str.substring(0, mid).toInt(), str.substring(mid).toInt())
            }
            else -> listOf(number * 2024)
        }
    }
}

fun main() {
    // Test if implementation meets criteria from the description
    val testInput = "125 17".lines()

    check(Day11().part1(testInput, 25) == 55312) { "Part 1 test failed" }
//    check(Day11().part2(testInput, 75) == 55312) { "Part 2 test failed" }

    val input = Utils.readInput(11)
    println("Part 1: ${Day11().part1(input, 25)}")
    println("Part 2: ${Day11().part2(input, 75)}")
}