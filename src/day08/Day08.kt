package day08

import day08.Day08.part1
import day08.Day08.part2
import printResult
import readInput

typealias Digit = Set<Char>

object Day08 {
    fun part1(input: List<String>) = input.map { it.split(" | ") }
        .map { (_, output) -> output.split(" ") }
        .flatten()
        .fold(0) { amount, digit -> if (digit.isDesiredDigit()) amount + 1 else amount }

    private fun String.isDesiredDigit() = count() in listOf(2, 3, 4, 7)

    fun part2(input: List<String>): Int {
        return input.fold(0) { result, display ->
            val (pattern, output) = display.split(" | ")
            val patterns = pattern.asSignal()
            val outputs = output.asSignal()

            val one = patterns.first { it.count() == 2 }
            val four = patterns.first { it.count() == 4 }

            val codes = patterns
                .associateWith { digit ->
                    when (digit.size) {
                        2 -> 1
                        3 -> 7
                        4 -> 4
                        7 -> 8
                        5 -> when {
                            digit.intersect(four).size == 2 -> 2
                            digit.intersect(one).size == 2 -> 3
                            else -> 5
                        }
                        else -> when { //6
                            digit.intersect(one).size == 1 -> 6
                            digit.intersect(four).size == 4 -> 9
                            else -> 0
                        }
                    }
                }

            result + outputs.map { codes[it]!! }.joinToString("").toInt()
        }
    }

    private fun String.asSignal(): List<Digit> = split(" ").map(String::toSet)
}

fun main() {
    val testInput = readInput("day08/Day08_test")
    with(part1(testInput)) { check(26 == this) { "result test 1: $this" } }
    with(part2(testInput)) { check(61229 == this) { "result test 2: $this" } }

    val input = readInput("day08/Day08")
    printResult("Part 1") { part1(input) }
    printResult("Part 2") { part2(input) }
}
