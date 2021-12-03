package day03

import printResult
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val measurements = input.size

        val mostCommonBit = input
            .map { it.chunked(1) { char -> char.toString().toInt() } }
            .reduce { acc, ints ->
                acc.zip(ints).map { it.first + it.second }
            }
            .map { if (it > measurements / 2) 1 else 0 }


        val gammaRate = mostCommonBit.toInt()
        val epsilonRate = mostCommonBit.map { if (it == 1) 0 else 1 }.toInt()

        return epsilonRate * gammaRate
    }


    fun part2(input: List<String>) = input.size

    val testInput = readInput("day03/Day03_test")
    with(part1(testInput)) { check(198 == this) { "result test 1: $this" } }
//    with(part2(testInput)) { check(? == this) { "result test 2: $this" } }

    val input = readInput("day03/Day03")
    printResult("Part 1") { part1(input) }
//    printResult("Part 2") { part2(input) }
}

fun List<Int>.toInt(radix: Int = 2) = joinToString("") { it.toString() }.toInt(radix)
