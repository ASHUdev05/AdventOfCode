//package aoc
//
//data class Region(
//    val type: Char,
//    val coordinates: Set<Pair<Int, Int>>,
//    val area: Int,
//    val perimeter: Int
//) {
//    val price: Int
//        get() = area * perimeter
//}
//
//class Day12 {
//    private fun findRegions(grid: List<String>): List<Region> {
//        val visited = mutableSetOf<Pair<Int, Int>>()
//        val regions = mutableListOf<Region>()
//
//        for (i in grid.indices) {
//            for (j in grid[0].indices) {
//                val coord = i to j
//                if (coord !in visited) {
//                    val type = grid[i][j]
//                    val regionCoords = mutableSetOf<Pair<Int, Int>>()
//
//                    // DFS to find all connected plots of same type
//                    fun dfs(row: Int, col: Int) {
//                        if (row !in grid.indices || col !in grid[0].indices) return
//                        if ((row to col) in visited) return
//                        if (grid[row][col] != type) return
//
//                        visited.add(row to col)
//                        regionCoords.add(row to col)
//
//                        // Check all 4 directions
//                        dfs(row + 1, col)
//                        dfs(row - 1, col)
//                        dfs(row, col + 1)
//                        dfs(row, col - 1)
//                    }
//
//                    dfs(i, j)
//
//                    // Calculate perimeter by checking each cell's edges
//                    var perimeter = 0
//                    for ((row, col) in regionCoords) {
//                        // Check all 4 sides
//                        val neighbors = listOf(
//                            row + 1 to col,
//                            row - 1 to col,
//                            row to col + 1,
//                            row to col - 1
//                        )
//
//                        for (neighbor in neighbors) {
//                            if (neighbor !in regionCoords) {
//                                perimeter++
//                            }
//                        }
//                    }
//
//                    regions.add(Region(type, regionCoords, regionCoords.size, perimeter))
//                }
//            }
//        }
//
//        return regions
//    }
//
//    fun part1(input: List<String>): Int {
//        val regions = findRegions(input)
//        return regions.sumOf { it.price }
//    }
//
//    fun part2(input: List<String>): Int {
//        // Part 2 implementation will be added when the problem description is provided
//        return 0
//    }
//}
//
//private data class Position(val x: Int, val y: Int) {
//    fun getNeighbours(map: Map) = listOfNotNull(
//        map.getOrElse(this.x - 1) { emptyList() }.getOrNull(this.y),
//        map.getOrElse(this.x + 1) { emptyList() }.getOrNull(this.y),
//        map.getOrElse(this.x) { emptyList() }.getOrNull(this.y - 1),
//        map.getOrElse(this.x) { emptyList() }.getOrNull(this.y + 1)
//    )
//
//    fun isAboveType(map: Map, type: Char): Boolean {
//        val aboveNeighbour = map.getOrElse(this.x - 1) { emptyList() }.getOrNull(this.y)
//        return aboveNeighbour?.second == type
//    }
//
//    fun isBelowType(map: Map, type: Char): Boolean {
//        val belowNeighbour = map.getOrElse(this.x + 1) { emptyList() }.getOrNull(this.y)
//        return belowNeighbour?.second == type
//    }
//
//    fun isLeftType(map: Map, type: Char): Boolean {
//        val leftNeighbour = map.getOrElse(this.x) { emptyList() }.getOrNull(this.y - 1)
//        return leftNeighbour?.second == type
//    }
//
//    fun isRightType(map: Map, type: Char): Boolean {
//        val rightNeighbour = map.getOrElse(this.x) { emptyList() }.getOrNull(this.y + 1)
//        return rightNeighbour?.second == type
//    }
//}
//
//private data class Region1(val type: Char, val plots: Set<Position>)
//private typealias Map = List<List<Pair<Position, Char>>>
//
//private fun getNumberOfSides(region: Region1, map: Map): Int {
//    var horizontalSides = 0
//    var verticalSides = 0
//
//    fun countSides(isCorrectType: Boolean, tracker: Boolean, increment: () -> Unit) =
//        if (!isCorrectType) {
//            if (!tracker) increment()
//            true
//        } else false
//
//    map.forEach { row ->
//        var aboveCorrectType = false
//        var belowCorrectType = false
//        row.forEach { plot ->
//            if (!region.plots.contains(plot.first)) {
//                aboveCorrectType = false
//                belowCorrectType = false
//                return@forEach
//            }
//            aboveCorrectType = countSides(plot.first.isAboveType(map, region.type), aboveCorrectType) {
//                horizontalSides++
//            }
//            belowCorrectType = countSides(plot.first.isBelowType(map, region.type), belowCorrectType) {
//                horizontalSides++
//            }
//        }
//    }
//
//    for (col in map[0].indices) {
//        var leftCorrectType = false
//        var rightCorrectType = false
//        for (row in map.indices) {
//            val plot = map[row][col]
//            if (!region.plots.contains(plot.first)) {
//                leftCorrectType = false
//                rightCorrectType = false
//                continue
//            }
//            leftCorrectType = countSides(plot.first.isLeftType(map, region.type), leftCorrectType) {
//                verticalSides++
//            }
//            rightCorrectType = countSides(plot.first.isRightType(map, region.type), rightCorrectType) {
//                verticalSides++
//            }
//        }
//    }
//
//    return horizontalSides + verticalSides
//}
//
//private fun getAllConnected(fromPos: Position, map: Map): Set<Position> {
//    val typeToFind = map[fromPos.x][fromPos.y].second
//    val toVisit = mutableListOf<Position>()
//    val visited = mutableSetOf<Position>()
//    val connected = mutableSetOf<Position>()
//
//    toVisit.add(fromPos)
//    visited.add(fromPos)
//
//    while (toVisit.isNotEmpty()) {
//        val current = toVisit.removeFirst()
//        connected.add(current)
//        current
//            .getNeighbours(map)
//            .filter { it.second == typeToFind && it.first !in visited }
//            .forEach {
//                toVisit.add(it.first)
//                visited.add(it.first)
//            }
//    }
//
//    return connected
//}
//
//private fun getRegions(map: Map): List<Region1> {
//    val regions = mutableListOf<Region1>()
//    val visited = map.flatten().associate { it.first to false } as MutableMap<Position, Boolean>
//
//    while (visited.values.contains(false)) {
//        val currentPos = visited.entries.first { !it.value }.key
//        val connected = getAllConnected(currentPos, map)
//        connected.forEach { visited[it] = true }
//        regions.add(Region1(map[currentPos.x][currentPos.y].second, connected))
//    }
//
//    return regions
//}
//
//fun solve(input: String): String {
//    val map = input
//        .lines()
//        .mapIndexed { i, line -> line.mapIndexed { j, c -> Pair(Position(i, j), c) } }
//
//    return getRegions(map)
//        .sumOf {
//            it.plots.size * getNumberOfSides(it, map)
//        }.toString()
//}
//
//
//fun main() {
//    // Test with the first example
//    val testInput1 = """
//        AAAA
//        BBCD
//        BBCC
//        EEEC
//    """.trimIndent().lines()
//
//    check(Day12().part1(testInput1) == 140) { "Part 1 test 1 failed" }
//
//    // Test with the second example (O's and X's)
//    val testInput2 = """
//        OOOOO
//        OXOXO
//        OOOOO
//        OXOXO
//        OOOOO
//    """.trimIndent().lines()
//
//    check(Day12().part1(testInput2) == 772) { "Part 1 test 2 failed" }
//
//    // Test with the larger example
//    val testInput3 = """
//        RRRRIICCFF
//        RRRRIICCCF
//        VVRRRCCFFF
//        VVRCCCJFFF
//        VVVVCJJCFE
//        VVIVCCJJEE
//        VVIIICJJEE
//        MIIIIIJJEE
//        MIIISIJEEE
//        MMMISSJEEE
//    """.trimIndent().lines()
//
//    check(Day12().part1(testInput3) == 1930) { "Part 1 test 3 failed" }
//
//    val input = Utils.readInput(12)
//    println("Part 1: ${Day12().part1(input)}")
//    println("Part 2: ${solve(input.joinToString("\n"))}")
//}