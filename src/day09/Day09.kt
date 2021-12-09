package day09

import day09.Day09.part1
import day09.Point.Position
import printResult
import readInput

typealias HeightMap = Array<Array<Point>>

object Day09 {
    fun part1(input: List<String>): Int {
        val points: HeightMap = input.mapIndexed { y, line ->
            line.mapIndexed { x, digit ->
                Point(x, y, digit.digitToInt())
            }.toTypedArray()
        }.toTypedArray()

        return points.lowPoints().sumOf { it.value + 1 }
    }

    private fun HeightMap.lowPoints() =
        flatten().filter { it.value < adjacent(it).minOf(Point::value) }

    private fun HeightMap.adjacent(point: Point) =
        listOf(point.position.top(), point.position.right(), point.position.bottom(), point.position.left())
            .mapNotNull { pointOf(it) }

    private fun HeightMap.pointOf(position: Position) = getOrNull(position.y)?.getOrNull(position.x)

    fun part2(input: List<String>): Int {
        return 1
    }
}

data class Point(val position: Position, val value: Int) {
    constructor(x: Int, y: Int, value: Int) : this(Position(x, y), value)

    data class Position(val x: Int, val y: Int) {
        fun top() = Position(x, y - 1)
        fun right() = Position(x + 1, y)
        fun bottom() = Position(x, y + 1)
        fun left() = Position(x - 1, y)
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
