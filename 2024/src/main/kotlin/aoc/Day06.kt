package aoc

@OptIn(ExperimentalUnsignedTypes::class)
class Day06 {
    @JvmInline
    private value class BitGrid(val data: ULongArray) {
        fun get(index: Int): Boolean =
            (data[index ushr 6] and (1UL shl (index and 63))) != 0UL

        fun set(index: Int) {
            data[index ushr 6] = data[index ushr 6] or (1UL shl (index and 63))
        }
    }

    // Directions: North, East, South, West
    private val directions = arrayOf(-1 to 0, 0 to 1, 1 to 0, 0 to -1)

    // Parse the input grid
    private fun parse(input: List<String>): Triple<Int, Int, BitGrid> {
        val width = input[0].length
        val height = input.size
        val size = width * height
        val grid = BitGrid(ULongArray((size + 63) ushr 6))
        var start = 0

        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, ch ->
                val index = y * width + x
                when (ch) {
                    '#' -> grid.set(index) // Wall
                    '^' -> start = index   // Guard's starting position
                }
            }
        }

        return Triple(start, width, grid)
    }

    // Simulate the guard's movement
    private fun simulateGuard(
        start: Int,
        width: Int,
        height: Int,
        grid: BitGrid,
        additionalWall: Int? = null,
        onVisit: ((Int, Int) -> Unit)? = null
    ): Boolean {
        val visited = mutableSetOf<Pair<Int, Int>>()
        var position = start
        var direction = 0 // Initial direction (North)

        while (true) {
            val state = position to direction

            // Detect loop
            if (state in visited) return true
            visited.add(state)

            // Notify visitation
            onVisit?.invoke(position, direction)

            // Calculate next position
            val nextX = (position % width) + directions[direction].second
            val nextY = (position / width) + directions[direction].first
            val nextIndex = nextY * width + nextX

            // Check bounds and obstacles
            if (nextX !in 0 until width || nextY !in 0 until height ||
                grid.get(nextIndex) || nextIndex == additionalWall
            ) {
                direction = (direction + 1) % 4 // Turn right
            } else {
                position = nextIndex
            }

            // If guard exits the grid
            if (nextX !in 0 until width || nextY !in 0 until height) return false
        }
    }

    // Part 1: Count visited locations
    fun part1(input: List<String>): Int {
        val (start, width, grid) = parse(input)
        val visited = mutableSetOf<Int>()

        simulateGuard(start, width, input.size, grid) { position, _ ->
            visited.add(position)
        }

        return visited.size
    }

    // Part 2: Count loop-causing obstructions
    fun part2(input: List<String>): Int {
        val (start, width, grid) = parse(input)
        val height = input.size

        // Get all visited locations in Part 1
        val visitedLocations = mutableSetOf<Int>()
        simulateGuard(start, width, height, grid) { position, _ ->
            visitedLocations.add(position)
        }

        // Check each visited location to see if it causes a loop
        return visitedLocations.count { loc ->
            simulateGuard(start, width, height, grid, additionalWall = loc)
        }
    }
}

fun main() {
    val testInput = """
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

    check(Day06().part1(testInput) == 41) { "Part 1 test failed" }
    check(Day06().part2(testInput) == 6) { "Part 2 test failed" }

    val input = Utils.readInput(6)
    println("Part 1: ${Day06().part1(input)}")
    println("Part 2: ${Day06().part2(input)}")
}