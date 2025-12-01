package aoc

class Day15 {

    fun part1(input: List<String>): Int {
        // Parse the warehouse map and movements
        val map = input.takeWhile { it.startsWith("#") }.map { it.toCharArray().toMutableList() }
        val moves = input.dropWhile { it.startsWith("#") }.joinToString("").filter { it in "^v<>" }

        // Print parsed input for debugging
        println("Initial Map:")
        map.forEach { println(it.joinToString("")) }
        println("Moves: $moves")

        // Find initial positions of the robot and boxes
        var robotPosition = Pair(0, 0)
        val boxPositions = mutableSetOf<Pair<Int, Int>>()

        for (y in map.indices) {
            for (x in map[y].indices) {
                when (map[y][x]) {
                    '@' -> {
                        robotPosition = Pair(y, x)
                        map[y][x] = '.' // Mark as empty
                    }
                    'O' -> boxPositions.add(Pair(y, x))
                }
            }
        }

        println("Initial Robot Position: $robotPosition")
        println("Initial Box Positions: $boxPositions")

        // Movement deltas
        val directions = mapOf(
            '^' to Pair(-1, 0),
            'v' to Pair(1, 0),
            '<' to Pair(0, -1),
            '>' to Pair(0, 1)
        )

        // Simulate the movements
        for (move in moves) {
            val (dy, dx) = directions[move] ?: continue
            val newRobotPosition = Pair(robotPosition.first + dy, robotPosition.second + dx)

            if (map[newRobotPosition.first][newRobotPosition.second] == '#') continue // Hit a wall

            if (newRobotPosition in boxPositions) {
                val newBoxPosition = Pair(newRobotPosition.first + dy, newRobotPosition.second + dx)
                if (map[newBoxPosition.first][newBoxPosition.second] == '#' || newBoxPosition in boxPositions) {
                    continue // Cannot push box into a wall or another box
                }
                // Move the box
                boxPositions.remove(newRobotPosition)
                boxPositions.add(newBoxPosition)
            }

            // Move the robot
            robotPosition = newRobotPosition

            // Print the state of the warehouse after each move
            println("After move $move:")
            map.forEachIndexed { y, row ->
                row.forEachIndexed { x, cell ->
                    print(
                        when {
                            Pair(y, x) == robotPosition -> '@'
                            Pair(y, x) in boxPositions -> 'O'
                            else -> cell
                        }
                    )
                }
                println()
            }
        }

        // Calculate the GPS coordinates for all boxes
        val gpsSum = boxPositions.sumOf { (y, x) -> y * 100 + x }
        println("Final Box Positions: $boxPositions")
        println("GPS Sum: $gpsSum")
        return gpsSum
    }


    fun part2(input: List<String>): Int {
        // Extend this for part 2 if required by further instructions
        return 0
    }
}

fun main() {
    val testInput = """
        ########
        #..O.O.#
        ##@.O..#
        #...O..#
        #.#.O..#
        #...O..#
        #......#
        ########
        <^^>>>vv<v>>v<<
    """.trimIndent().lines()

    val day15 = Day15()
    check(day15.part1(testInput) == 2028) { "Part 1 test failed" }

    val input = Utils.readInput(15)
    println("Part 1: ${day15.part1(input)}")
    println("Part 2: ${day15.part2(input)}")
}
