package day08

import day08.Day08.part1
import printResult
import readInput

object Day08 {
    fun part1(input: List<String>) = input.map { it.split(" | ") }
        .map { (_, output) -> output.split(" ") }
        .flatten()
        .fold(0) { amount, digit -> if (digit.isDesiredDigit()) amount + 1 else amount }

    private fun String.isDesiredDigit() = count() in listOf(2, 3, 4, 7)

    fun part2(input: List<String>): Int {
        return 1
    }
}

fun main() {
    val testInput = readInput("day08/Day08_test")
    with(part1(testInput)) { check(26 == this) { "result test 1: $this" } }
//    with(part2(testInput)) { check(? == this) { "result test 2: $this" } }

    val input = readInput("day08/Day08")
    printResult("Part 1") { part1(input) }
//    printResult("Part 2") { part2(input) }
}
