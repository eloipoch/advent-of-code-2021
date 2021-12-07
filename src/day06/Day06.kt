package day06

import countBy
import day06.Day06.part1
import day06.Day06.part2
import printResult
import readInput
import toInts

object Day06 {
    fun part1(input: List<String>): Int {
        val lanterns = LanternsSchool(input.first().split(",").toInts())

        repeat(80) { lanterns.cycle() }

        return lanterns.count.toInt()
    }

    fun part2(input: List<String>): Long {
        val lanterns = LanternsSchool(input.first().split(",").toInts())

        repeat(256) { lanterns.cycle() }

        return lanterns.count
    }

    class LanternsSchool(private var lanterns: Map<Int, Long>) {
        constructor(lanterns: List<Int>) : this(lanterns.countBy().mapValues { it.value.toLong() })

        private val newborns get() = amount(0)
        val count get() = lanterns.values.sum()

        fun cycle() {
            lanterns = (0..8).associateWith { timer ->
                when (timer) {
                    6 -> amount(timer + 1) + newborns
                    8 -> newborns
                    else -> amount(timer + 1)
                }
            }
        }

        private fun amount(timer: Int) = lanterns.getOrDefault(timer, 0)
    }
}

fun main() {
    val testInput = readInput("day06/Day06_test")
    with(part1(testInput)) { check(5934 == this) { "result test 1: $this" } }
    with(part2(testInput)) { check(26984457539 == this) { "result test 2: $this" } }

    val input = readInput("day06/Day06")
    printResult("Part 1") { part1(input) }
    printResult("Part 2") { part2(input) }
}
