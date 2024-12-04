package aoc

import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

object Utils {
    /**
     * Reads lines from the given input txt file.
     */
    fun readInput(day: Int): List<String> {
        return File("src/main/resources/day${day.toString().padStart(2, '0')}.txt")
            .readLines()
    }

    /**
     * Reads input file as a single string, splitting on blank lines
     */
    fun readInputAsGroups(day: Int): List<String> {
        return File("src/main/resources/day${day.toString().padStart(2, '0')}.txt")
            .readText()
            .split("\n\n")
    }

    /**
     * Reads lines and converts to integers
     */
    fun readInputAsInts(day: Int): List<Int> {
        return readInput(day).map { it.toInt() }
    }

    /**
     * Converts string to md5 hash
     */
    fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5")
        .digest(toByteArray()))
        .toString(16)
        .padStart(32, '0')

    /**
     * Splits a list into chunks of specified size
     */
    fun <T> List<T>.chunked(size: Int): List<List<T>> {
        return chunked(size)
    }

    /**
     * Converts a string of digits to list of integers
     */
    fun String.toDigits(): List<Int> {
        return map { it.toString().toInt() }
    }

    /**
     * Greatest Common Divisor of two numbers
     */
    fun gcd(a: Long, b: Long): Long {
        if (b == 0L) return a
        return gcd(b, a % b)
    }

    /**
     * Least Common Multiple of two numbers
     */
    fun lcm(a: Long, b: Long): Long {
        return a * (b / gcd(a, b))
    }

    /**
     * Manhattan distance between two points
     */
    fun manhattanDistance(x1: Int, y1: Int, x2: Int, y2: Int): Int {
        return kotlin.math.abs(x1 - x2) + kotlin.math.abs(y1 - y2)
    }
}