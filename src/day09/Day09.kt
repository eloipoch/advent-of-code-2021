package day09

import day09.Day09.part1
import printResult
import readInput

typealias HeightMap = Array<Array<Int>>

object Day09 {
    fun part1(input: List<String>): Int {
        val points = input.map { it.map(Char::digitToInt).toTypedArray() }.toTypedArray()

        return points.mapIndexed { y, line ->
            line.filterIndexed { x, point -> point < points.adjacent(y, x).minOf { it } }
        }.flatten().sumOf { it + 1 }
    }

    private fun HeightMap.adjacent(y: Int, x: Int) =
        listOfNotNull(right(y, x), bottom(y, x), left(y, x), top(y, x))

    private fun HeightMap.left(y: Int, x: Int) = getOrNull(y)?.getOrNull(x - 1)
    private fun HeightMap.bottom(y: Int, x: Int) = getOrNull(y + 1)?.getOrNull(x)
    private fun HeightMap.right(y: Int, x: Int) = getOrNull(y)?.getOrNull(x + 1)
    private fun HeightMap.top(y: Int, x: Int) = getOrNull(y - 1)?.getOrNull(x)

    fun part2(input: List<String>): Int {
        return 1
    }
}

fun main() {
    val testInput = readInput("day09/Day09_test")
    with(part1(testInput)) { check(15 == this) { "result test 1: $this" } }
//    with(part2(testInput)) { check(? == this) { "result test 2: $this" } }

    val input = readInput("day09/Day09")
    printResult("Part 1") { part1(input) }
//    printResult("Part 2") { part2(input) }
}
