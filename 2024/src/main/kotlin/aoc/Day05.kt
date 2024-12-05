package aoc

class Day05 {
    private fun parseInput(input: List<String>): Pair<Map<Int, Set<Int>>, List<List<Int>>> {
        val rules = mutableMapOf<Int, MutableSet<Int>>()
        val sequences = mutableListOf<List<Int>>()
        var parsingRules = true

        for (line in input) {
            if (line.isEmpty()) {
                parsingRules = false
                continue
            }

            if (parsingRules) {
                val (before, after) = line.split("|").map { it.toInt() }
                rules.getOrPut(before) { mutableSetOf() }.add(after)
            } else {
                sequences.add(line.split(",").map { it.toInt() })
            }
        }

        return rules to sequences
    }

    private fun isValidSequence(sequence: List<Int>, rules: Map<Int, Set<Int>>): Boolean {
        // For each pair of numbers in the sequence
        for (i in sequence.indices) {
            for (j in i + 1 until sequence.size) {
                val before = sequence[i]
                val after = sequence[j]

                // Check if there's a rule requiring the opposite order
                rules[after]?.let { afterMustBeFirst ->
                    if (before in afterMustBeFirst) {
                        return false
                    }
                }
            }
        }
        return true
    }

    private fun sortSequence(sequence: List<Int>, rules: Map<Int, Set<Int>>): List<Int> {
        // Build complete dependency graph
        val graph = mutableMapOf<Int, MutableSet<Int>>()
        val inDegree = mutableMapOf<Int, Int>()

        // Initialize for all nodes in sequence
        sequence.forEach { page ->
            graph[page] = mutableSetOf()
            inDegree[page] = 0
        }

        // Build edges from rules
        for (i in sequence.indices) {
            for (j in sequence.indices) {
                val page1 = sequence[i]
                val page2 = sequence[j]
                if (i != j) {
                    // If there's a rule page2 -> page1, add edge page1 -> page2
                    rules[page2]?.let { dependencies ->
                        if (page1 in dependencies) {
                            graph[page1]?.add(page2)
                            inDegree[page2] = (inDegree[page2] ?: 0) + 1
                        }
                    }
                }
            }
        }

        // Topological sort
        val result = mutableListOf<Int>()
        val queue = sequence.filter { inDegree[it] == 0 }.toMutableList()

        while (queue.isNotEmpty()) {
            val page = queue.removeFirst()
            result.add(page)

            graph[page]?.forEach { next ->
                inDegree[next] = (inDegree[next] ?: 1) - 1
                if (inDegree[next] == 0) {
                    queue.add(next)
                }
            }
        }

        return result
    }

    fun part1(input: List<String>): Int {
        val (rules, sequences) = parseInput(input)

        // Find valid sequences and their middle numbers
        return sequences
            .filter { isValidSequence(it, rules) }
            .sumOf { it[it.size / 2] }
    }

    fun part2(input: List<String>): Int {
        val (rules, sequences) = parseInput(input)

        // Find invalid sequences, sort them, and sum their middle numbers
        return sequences
            .filterNot { isValidSequence(it, rules) }
            .map { sortSequence(it, rules) }
            .sumOf { it[it.size / 2] }
    }
}

fun main() {
    // Test if implementation meets criteria from the description
    val testInput = """
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

    check(Day05().part1(testInput) == 143) { "Part 1 test failed" }
    check(Day05().part2(testInput) == 123) { "Part 2 test failed" }

    val input = Utils.readInput(5)
    println("Part 1: ${Day05().part1(input)}")
    println("Part 2: ${Day05().part2(input)}")
}