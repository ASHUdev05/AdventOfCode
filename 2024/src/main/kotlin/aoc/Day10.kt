//package aoc
//
//data class Point(val x: Int, val y: Int)
//
//class Day10 {
//    private fun getNeighbors(point: Point1, grid: List<List<Int>>): List<Point1> {
//        val (x, y) = point
//        return listOf(
//            Point1(x - 1, y), Point1(x + 1, y),
//            Point1(x, y - 1), Point1(x, y + 1)
//        ).filter { (nx, ny) ->
//            nx in grid[0].indices && ny in grid.indices
//        }
//    }
//
//    private fun findTrailsFromPoint(
//        start: Point1,
//        grid: List<List<Int>>,
//        visited: MutableSet<Point1> = mutableSetOf(),
//        currentHeight: Int = 0
//    ): Set<Point1> {
//        if (currentHeight == 9) {
//            return setOf(start)
//        }
//
//        val reachableNines = mutableSetOf<Point1>()
//        visited.add(start)
//
//        for (neighbor in getNeighbors(start, grid)) {
//            if (neighbor !in visited) {
//                val neighborHeight = grid[neighbor.y][neighbor.x]
//                if (neighborHeight == currentHeight + 1) {
//                    reachableNines.addAll(
//                        findTrailsFromPoint(neighbor, grid, visited, neighborHeight)
//                    )
//                }
//            }
//        }
//
//        visited.remove(start)
//        return reachableNines
//    }
//
//    private fun countDistinctTrails(
//        current: Point1,
//        grid: List<List<Int>>,
//        path: MutableSet<Point1> = mutableSetOf(),
//        currentHeight: Int = 0,
//        memo: MutableMap<Point1, Int> = mutableMapOf()
//    ): Int {
//        if (currentHeight == 9) {
//            return 1
//        }
//
//        if (current in memo) {
//            return memo[current]!!
//        }
//
//        var totalPaths = 0
//        path.add(current)
//
//        for (neighbor in getNeighbors(current, grid)) {
//            if (neighbor !in path) {
//                val neighborHeight = grid[neighbor.y][neighbor.x]
//                if (neighborHeight == currentHeight + 1) {
//                    totalPaths += countDistinctTrails(neighbor, grid, path, neighborHeight, memo)
//                }
//            }
//        }
//
//        path.remove(current)
//        memo[current] = totalPaths
//        return totalPaths
//    }
//
//    fun part1(input: List<String>): Int {
//        val grid = input.map { line -> line.map { it.digitToInt() } }
//        val trailheads = mutableListOf<Point1>()
//
//        // Find all trailheads (positions with height 0)
//        for (y in grid.indices) {
//            for (x in grid[0].indices) {
//                if (grid[y][x] == 0) {
//                    trailheads.add(Point1(x, y))
//                }
//            }
//        }
//
//        // Calculate score for each trailhead
//        return trailheads.sumOf { trailhead ->
//            findTrailsFromPoint(trailhead, grid).size
//        }
//    }
//
//    fun part2(input: List<String>): Int {
//        val grid = input.map { line -> line.map { it.digitToInt() } }
//        val trailheads = mutableListOf<Point1>()
//
//        // Find all trailheads (positions with height 0)
//        for (y in grid.indices) {
//            for (x in grid[0].indices) {
//                if (grid[y][x] == 0) {
//                    trailheads.add(Point1(x, y))
//                }
//            }
//        }
//
//        // Calculate rating (number of distinct paths) for each trailhead
//        return trailheads.sumOf { trailhead ->
//            countDistinctTrails(trailhead, grid)
//        }
//    }
//}
//
//fun main() {
//    // Test with examples from Part 2
//    val testInput1 = """
//        012345
//        123456
//        234567
//        345678
//        4.6789
//        56789.
//    """.trimIndent().lines()
//
//    val testInput2 = """
//        89010123
//        78121874
//        87430965
//        96549874
//        45678903
//        32019012
//        01329801
//        10456732
//    """.trimIndent().lines()
//
//    val day10 = Day10()
//    check(day10.part2(testInput2) == 81) { "Failed test with larger example" }
//
//    val input = Utils.readInput(10)
//    println("Part 1: ${day10.part1(input)}")
//    println("Part 2: ${day10.part2(input)}")
//}