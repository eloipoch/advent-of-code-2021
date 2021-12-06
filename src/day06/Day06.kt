package day06

import day06.Day06.part1
import printResult
import readInput
import toInts

object Day06 {
    fun part1(input: List<String>): Int {
        var lanterns = input.first().split(",").toInts().map(::LanternFish)

        repeat(80) {
            lanterns = lanterns.fold(lanterns) { lanterns, lantern ->
                lantern.cycle()?.let { lanterns + it } ?: lanterns
            }
        }

        return lanterns.size
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    data class LanternFish(private var timer: Int) {
        fun cycle(): LanternFish? {
            var lantern: LanternFish? = null

            if (timer == 0) {
                timer = 7
                lantern = LanternFish(8)
            }

            timer--

            return lantern
        }
    }
}

fun main() {
    val testInput = readInput("day06/Day06_test")
    with(part1(testInput)) { check(5934 == this) { "result test 1: $this" } }
//    with(part2(testInput)) { check(? == this) { "result test 2: $this" } }

    val input = readInput("day06/Day06")
    printResult("Part 1") { part1(input) }
//    printResult("Part 2") { part2(input) }
}
