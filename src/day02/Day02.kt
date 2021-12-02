package day02

import printResult
import readInput

fun main() {
    fun part1(input: List<String>) = input
        .map { it.split(" ") }
        .map { (direction, points) -> direction to points.toInt() }
        .fold(0 to 0) { (horizontal, depth), (direction, points) ->
            when (direction) {
                "forward" -> horizontal + points to depth
                "down" -> horizontal to depth + points
                "up" -> horizontal to depth - points
                else -> horizontal to depth
            }
        }
        .run { first * second }

    fun part2(input: List<String>) = input.size

    val testInput = readInput("day02/Day02_test")

    printResult("Test 1") { part1(testInput) }
    printResult("Test 2") { part2(testInput) }

    val input = readInput("day02/Day02")
    printResult("Part 1") { part1(input) }
    printResult("Part 2") { part2(input) }
}
