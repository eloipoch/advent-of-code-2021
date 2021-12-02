package day01

import printResult
import readInput
import toInts

fun main() {
    fun part1(input: List<String>) = input.toInts()
        .zipWithNext()
        .count { (first, second) -> first < second }

    fun part2(input: List<String>) = input.toInts()
        .windowed(3) { it.sum() }
        .zipWithNext()
        .count { (first, second) -> first < second }

    val testInput = readInput("day01/Day01_test")
    with(part1(testInput)) { check(7 == this) { "result test 1: $this" } }
    with(part2(testInput)) { check(5 == this) { "result test 2: $this" } }

    val input = readInput("day01/Day01")
    printResult("Part 1") { part1(input) }
    printResult("Part 2") { part2(input) }
}
