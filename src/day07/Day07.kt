package day07

import day07.Day07.part1
import day07.Day07.part2
import printResult
import readInput
import splitToInts
import triangular
import kotlin.Int.Companion.MAX_VALUE
import kotlin.math.abs
import kotlin.math.min

object Day07 {
    fun part1(input: List<String>): Int {
        val crabs = input.first().splitToInts()
        val min = crabs.minOf { it }
        val max = crabs.maxOf { it }

        return (min..max).fold(MAX_VALUE) { best, current ->
            min(best, crabs.sumOf { abs(it - current) })
        }
    }

    fun part2(input: List<String>): Int {
        val crabs = input.first().splitToInts()
        val min = crabs.minOf { it }
        val max = crabs.maxOf { it }

        return (min..max).fold(MAX_VALUE) { best, current ->
            min(best, crabs.sumOf { abs(it - current).triangular() })
        }
    }
}

fun main() {
    val testInput = readInput("day07/Day07_test")
    with(part1(testInput)) { check(37 == this) { "result test 1: $this" } }
    with(part2(testInput)) { check(168 == this) { "result test 2: $this" } }

    val input = readInput("day07/Day07")
    printResult("Part 1") { part1(input) }
    printResult("Part 2") { part2(input) }
}
