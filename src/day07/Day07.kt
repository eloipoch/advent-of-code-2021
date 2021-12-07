package day07

import day07.Day07.part1
import printResult
import readInput
import toInts
import kotlin.Long.Companion.MAX_VALUE
import kotlin.math.abs
import kotlin.math.min

object Day07 {
    fun part1(input: List<String>): Long {
        val crabs = input.first().split(",").toInts()
        val min = crabs.minOf { it }
        val max = crabs.maxOf { it }

        return (min..max).fold(MAX_VALUE) { best, current ->
            min(
                best,
                crabs.fold(0L) { movements, crab -> movements + abs(crab - current) }
            )
        }
    }

    fun part2(input: List<String>): Int {
        return 1
    }
}

fun main() {
    val testInput = readInput("day07/Day07_test")
    with(part1(testInput)) { check(37L == this) { "result test 1: $this" } }
//    with(part2(testInput)) { check(? == this) { "result test 2: $this" } }

    val input = readInput("day07/Day07")
    printResult("Part 1") { part1(input) }
//    printResult("Part 2") { part2(input) }
}
