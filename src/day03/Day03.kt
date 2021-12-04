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


    fun part2(input: List<String>): Int {
        fun List<List<Int>>.oxigen(bit: Int = 0): List<Int> {
            val mostCommon = groupBy { it[bit] }
                .filter { it.value.size.toDouble() > size / 2.0 || (it.key == 1 && it.value.size.toDouble() == size / 2.0) }
                .values.first()

            return if (mostCommon.size == 1) mostCommon.first() else mostCommon.oxigen(bit + 1)
        }

        fun List<List<Int>>.co2(bit: Int = 0): List<Int> {
            val leastCommon = groupBy { it[bit] }
                .filter { it.value.size.toDouble() < size / 2.0 || (it.key == 0 && it.value.size.toDouble() == size / 2.0) }
                .values.first()

            return if (leastCommon.size == 1) leastCommon.first() else leastCommon.co2(bit + 1)
        }

        val measurements = input
            .map { it.chunked(1) { char -> char.toString().toInt() } }

        return measurements.oxigen().toInt() * measurements.co2().toInt()
    }

    val testInput = readInput("day03/Day03_test")
    with(part1(testInput)) { check(198 == this) { "result test 1: $this" } }
    with(part2(testInput)) { check(230 == this) { "result test 2: $this" } }

    val input = readInput("day03/Day03")
    printResult("Part 1") { part1(input) }
    printResult("Part 2") { part2(input) }
}

fun List<Int>.toInt(radix: Int = 2) = joinToString("") { it.toString() }.toInt(radix)
