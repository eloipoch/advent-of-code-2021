package day01

import printResult
import readInput
import toInts

fun main() {
    fun part1(input: List<String>) = input.toInts()
        .zipWithNext { first, second -> if (first < second) "inc" else "other" }
        .count { "inc" == it }

    fun part2(input: List<String>) = input.toInts()
        .windowed(3) { it.sum() }
        .zipWithNext { first, second -> if (first < second) "inc" else "other" }
        .count { "inc" == it }

    val testInput = readInput("day01/Day01_test")
    printResult("Test 1") { part1(testInput) }
    printResult("Test 2") { part2(testInput) }

    val input = readInput("day01/Day01")
    printResult("Part 1") { part1(input) }
    printResult("Part 2") { part2(input) }
}
