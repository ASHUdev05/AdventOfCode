package aoc

class Day14 {

    data class Robot(var x: Int, var y: Int, val vx: Int, val vy: Int)

    fun part1(input: List<String>): Int {
        val width = 101
        val height = 103
        val robots = input.map { line ->
            val (px, py, vx, vy) = Regex("p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)")
                .find(line)!!
                .destructured
            Robot(px.toInt(), py.toInt(), vx.toInt(), vy.toInt())
        }

        // Simulate 100 seconds
        repeat(100) {
            robots.forEach { robot ->
                robot.x = (robot.x + robot.vx + width) % width
                robot.y = (robot.y + robot.vy + height) % height
            }
        }

        // Count robots in each quadrant
        val midX = width / 2
        val midY = height / 2
        val quadrants = IntArray(4) { 0 }

        robots.forEach { robot ->
            when {
                robot.x < midX && robot.y < midY -> quadrants[0]++ // Top-left
                robot.x >= midX && robot.y < midY -> quadrants[1]++ // Top-right
                robot.x < midX && robot.y >= midY -> quadrants[2]++ // Bottom-left
                robot.x >= midX && robot.y >= midY -> quadrants[3]++ // Bottom-right
            }
        }

        // Calculate safety factor
        return quadrants.fold(1) { acc, count -> acc * count }
    }

    fun part2(input: List<String>): Int {
        val width = 101
        val height = 103
        val robots = input.map { line ->
            val (px, py, vx, vy) = Regex("p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)")
                .find(line)!!
                .destructured
            Robot(px.toInt(), py.toInt(), vx.toInt(), vy.toInt())
        }

        var seconds = 0
        while (true) {
            // Update positions
            robots.forEach { robot ->
                robot.x = (robot.x + robot.vx + width) % width
                robot.y = (robot.y + robot.vy + height) % height
            }

            // Calculate bounding box
            val minX = robots.minOf { it.x }
            val maxX = robots.maxOf { it.x }
            val minY = robots.minOf { it.y }
            val maxY = robots.maxOf { it.y }

            val boundingWidth = maxX - minX + 1
            val boundingHeight = maxY - minY + 1

            // Check if the bounding box is sufficiently small
            if (boundingWidth <= 50 && boundingHeight <= 10) {
                // Visualize the positions
                val grid = Array(boundingHeight) { CharArray(boundingWidth) { '.' } }
                robots.forEach { robot ->
                    grid[robot.y - minY][robot.x - minX] = '#'
                }
                println("After $seconds seconds:")
                grid.forEach { println(it.joinToString("")) }
                break
            }

            seconds++
        }

        return seconds
    }
}

fun main() {
    // Test input example
    val testInput = """
        p=0,4 v=3,-3
        p=6,3 v=-1,-3
        p=10,3 v=-1,2
        p=2,0 v=2,-1
        p=0,0 v=1,3
        p=3,0 v=-2,-2
        p=7,6 v=-1,-3
        p=3,0 v=-1,-2
        p=9,3 v=2,3
        p=7,3 v=-1,2
        p=2,4 v=2,-3
        p=9,5 v=-3,-3
    """.trimIndent().lines()

    val day14 = Day14()
    val testResult = day14.part1(testInput)
    println("Test Result: $testResult") // Expected output: 12

    val input = Utils.readInput(14)
    println("Part 1: ${day14.part1(input)}")
    println("Part 2: ${day14.part2(input)}")
}
