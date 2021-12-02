package day01

import printResult
import readInput

fun main() {
    fun part1(input: List<String>) = input
        .map { it.toInt() }
        .zipWithNext { first, second -> if (first < second) "inc" else "dec" }
        .count { "inc" == it }

    fun part2(input: List<String>): Int {
        return input.size
    }

    printResult("Test") { part1(readInput("day01/Day01_test")) }

    val input = readInput("day01/Day01")

    printResult("Part 1") { part1(input) }
    printResult("Part 2") { part2(input) }
}
