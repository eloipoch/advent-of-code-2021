package day09

import day09.Day09.part1
import day09.Day09.part2
import day09.Point.Position
import printResult
import readInput

typealias HeightMap = Array<Array<Point>>

object Day09 {
    fun part1(input: List<String>) = heightMap(input).lowPoints().sumOf { it.value + 1 }

    fun part2(input: List<String>): Int {
        val heightMap = heightMap(input)

        return heightMap.lowPoints()
            .map { heightMap.basinOf(it).count() }
            .sortedDescending()
            .take(3)
            .reduce { result, basin -> result * basin }
    }

    private fun HeightMap.lowPoints() =
        flatten().filter { point -> point < adjacent(point).minOf { it } }

    private fun HeightMap.adjacent(point: Point) =
        listOf(point.position.top(), point.position.right(), point.position.bottom(), point.position.left())
            .mapNotNull { pointOf(it) }

    private fun HeightMap.pointOf(position: Position) = getOrNull(position.y)?.getOrNull(position.x)

    private fun heightMap(input: List<String>): HeightMap = input.mapIndexed { y, line ->
        line.mapIndexed { x, digit -> Point(x, y, digit.digitToInt()) }.toTypedArray()
    }.toTypedArray()

    private fun HeightMap.basinOf(point: Point): Set<Point> {
        val moreBasin = adjacent(point)
            .filterNot { it.value == 9 }
            .filter { it > point }

        return if (moreBasin.isEmpty()) {
            setOf(point)
        } else {
            moreBasin.fold(setOf(point)) { basin, another -> basin + basinOf(another) }
        }
    }
}

data class Point(val position: Position, val value: Int) : Comparable<Point> {
    constructor(x: Int, y: Int, value: Int) : this(Position(x, y), value)

    data class Position(val x: Int, val y: Int) {
        fun top() = Position(x, y - 1)
        fun right() = Position(x + 1, y)
        fun bottom() = Position(x, y + 1)
        fun left() = Position(x - 1, y)
    }

    override fun compareTo(other: Point) = value.compareTo(other.value)
}

fun main() {
    val testInput = readInput("day09/Day09_test")
    with(part1(testInput)) { check(15 == this) { "result test 1: $this" } }
    with(part2(testInput)) { check(1134 == this) { "result test 2: $this" } }

    val input = readInput("day09/Day09")
    printResult("Part 1") { part1(input) }
    printResult("Part 2") { part2(input) }
}
