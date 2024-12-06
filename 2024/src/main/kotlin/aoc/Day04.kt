package aoc

class Day04 {
    // Part 1: Find all instances of "XMAS"
    private val directions = listOf(
        Pair(0, 1),   // Right
        Pair(0, -1),  // Left
        Pair(1, 0),   // Down
        Pair(-1, 0),  // Up
        Pair(1, 1),   // Down-Right
        Pair(-1, -1), // Up-Left
        Pair(1, -1),  // Down-Left
        Pair(-1, 1)   // Up-Right
    )

    private fun checkWord(grid: List<String>, row: Int, col: Int, dRow: Int, dCol: Int): Boolean {
        val target = "XMAS"
        if (row + (target.length - 1) * dRow !in grid.indices ||
            col + (target.length - 1) * dCol !in grid[0].indices) {
            return false
        }

        var word = ""
        for (i in target.indices) {
            word += grid[row + i * dRow][col + i * dCol]
        }
        return word == target
    }

    fun part1(input: List<String>): Int {
        var count = 0
        for (row in input.indices) {
            for (col in input[0].indices) {
                for (dir in directions) {
                    if (checkWord(input, row, col, dir.first, dir.second)) {
                        count++
                    }
                }
            }
        }
        return count
    }

    // Part 2: Find X patterns of "MAS"
    private fun checkXMAS(grid: List<String>, startRow: Int, startCol: Int): Boolean {
        if (startRow + 2 >= grid.size || startCol + 2 >= grid[0].length) {
            return false
        }

        val topLeft = grid[startRow][startCol]
        val middle = grid[startRow + 1][startCol + 1]
        val topRight = grid[startRow][startCol + 2]
        val bottomLeft = grid[startRow + 2][startCol]
        val bottomRight = grid[startRow + 2][startCol + 2]

        fun isValidMAS(a: Char, b: Char, c: Char): Boolean {
            return (a == 'M' && b == 'A' && c == 'S') ||
                    (a == 'S' && b == 'A' && c == 'M')
        }

        val diagonal1Valid = isValidMAS(topLeft, middle, bottomRight)
        val diagonal2Valid = isValidMAS(topRight, middle, bottomLeft)

        return diagonal1Valid && diagonal2Valid
    }

    fun part2(input: List<String>): Int {
        var count = 0
        for (row in 0..input.size - 3) {
            for (col in 0..input[0].length - 3) {
                if (checkXMAS(input, row, col)) {
                    count++
                }
            }
        }
        return count
    }
}

fun main() {
    val testInput = """
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

    check(Day04().part1(testInput) == 18) { "Part 1 test failed" }
    check(Day04().part2(testInput) == 9) { "Part 2 test failed" }

    val input = Utils.readInput(4)
    println("Part 1: ${Day04().part1(input)}")
    println("Part 2: ${Day04().part2(input)}")
}