package day02

import printResult
import readInput

fun main() {
    fun part1(input: List<String>) = input
        .map { it.split(" ") }
        .map { (direction, units) -> direction to units.toInt() }
        .fold(0 to 0) { (horizontal, depth), (direction, units) ->
            when (direction) {
                "forward" -> horizontal + units to depth
                "down" -> horizontal to depth + units
                "up" -> horizontal to depth - units
                else -> horizontal to depth
            }
        }
        .run { first * second }

    fun part2(input: List<String>) = input
        .map { it.split(" ") }
        .map { (direction, units) -> direction to units.toInt() }
        .fold(Position.create()) { position, (direction, units) ->
            when (direction) {
                "forward" -> position.forward(units)
                "down" -> position.down(units)
                "up" -> position.up(units)
                else -> position
            }
        }.result()

    val testInput = readInput("day02/Day02_test")

    printResult("Test 1") { part1(testInput) }
    printResult("Test 2") { part2(testInput) }

    val input = readInput("day02/Day02")
    printResult("Part 1") { part1(input) }
    printResult("Part 2") { part2(input) }
}

data class Position(val horizontal: Int, val depth: Int, val aim: Int) {
    fun down(units: Int) = copy(aim = aim + units)
    fun up(units: Int) = copy(aim = aim - units)
    fun forward(units: Int) = Position(horizontal + units, depth + (aim * units), aim)

    fun result() = horizontal * depth

    companion object {
        fun create() = Position(0, 0, 0)
    }
}
