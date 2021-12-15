package day11

import Position
import adjacent
import day11.Day11.part1
import day11.Day11.part2
import printResult
import readInput

object Day11 {

    fun part1(input: List<String>): Int {
        val octopuses = Octopuses.parse(input)

        return (1..100).sumOf {
            octopuses.charge()
            octopuses.flash()
        }
    }

    fun part2(input: List<String>): Int {
        val octopuses = Octopuses.parse(input)

        return steps(octopuses)
    }

    data class Octopus(var energy: Int) {
        val flashed get() = energy == 0

        fun flash() = if (energy > 9) {
            energy = 0
            true
        } else {
            false
        }

        fun charge() {
            energy++
        }
    }

    class Octopuses(private val octopuses: List<List<Octopus>>) : List<List<Octopus>> by octopuses {
        val amount = flatten().size

        fun flash(total: Int = 0): Int {
            val flashed = octopuses.flatMapIndexed { y, line ->
                line.mapIndexedNotNull { x, octopus -> if (octopus.flash()) Position(x, y) else null }
            }

            flashed.flatMap { adjacent(it).filterNot(Octopus::flashed) }.onEach(Octopus::charge)

            return if (flashed.isEmpty()) total else flash(total + flashed.size)
        }

        fun charge() {
            map { it.map(Octopus::charge) }
        }

        companion object {
            fun parse(grid: List<String>) = Octopuses(
                grid.map { line -> line.map { energy -> Octopus(energy.digitToInt()) } }
            )
        }
    }

    private fun steps(octopuses: Octopuses, counter: Int = 1): Int {
        octopuses.charge()

        return if (octopuses.amount == octopuses.flash()) counter else steps(octopuses, counter + 1)
    }
}


fun main() {
    val testInput = readInput("day11/Day11_test")
    with(part1(testInput)) { check(1656 == this) { "result test 1: $this" } }
    with(part2(testInput)) { check(195 == this) { "result test 2: $this" } }

    val input = readInput("day11/Day11")
    printResult("Part 1") { part1(input) }
    printResult("Part 2") { part2(input) }
}

