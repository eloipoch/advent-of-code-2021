package day05

import day05.Vector.Horizontal
import day05.Vector.Vertical
import printResult
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val vents = input.map { it.split(" -> ") }
            .map { (x, y) -> x.asPoint()..y.asPoint() }
            .filter { it is Horizontal || it is Vertical }

        return vents.flatten()
            .groupingBy { it }
            .eachCount()
            .filter { (_, times) -> times >= 2 }
            .count()
    }

    fun part2(input: List<String>): Int {
        val vents = input.map { it.split(" -> ") }
            .map { (x, y) -> x.asPoint()..y.asPoint() }

        return vents.flatten()
            .groupingBy { it }
            .eachCount()
            .filter { (_, times) -> times >= 2 }
            .count()
    }

    val testInput = readInput("day05/Day05_test")
    with(part1(testInput)) { check(5 == this) { "result test 1: $this" } }
    with(part2(testInput)) { check(12 == this) { "result test 2: $this" } }

    val input = readInput("day05/Day05")
    printResult("Part 1") { part1(input) }
    printResult("Part 2") { part2(input) }
}

sealed class Vector(from: Point, to: Point) : Iterable<Point>, ClosedRange<Point> {
    class Horizontal(from: Point, to: Point) : Vector(from, to)
    class Vertical(from: Point, to: Point) : Vector(from, to)
    class Diagonal(from: Point, to: Point) : Vector(from, to)

    override val start: Point = minOf(from, to)
    override val endInclusive: Point = maxOf(from, to)

    override fun iterator(): Iterator<Point> = VectorIterator(this)

    companion object {
        operator fun invoke(from: Point, to: Point) = when (true) {
            from.x == to.x -> Horizontal(from, to)
            from.y == to.y -> Vertical(from, to)
            else -> Diagonal(from, to)
        }
    }
}

data class Point(val x: Int, val y: Int) : Comparable<Point> {
    override fun compareTo(other: Point): Int {
        return if (x != other.x) x - other.x else y - other.y
    }

    operator fun rangeTo(other: Point) = Vector(this, other)
}

class VectorIterator(private val vector: Vector) : Iterator<Point> {
    private val step: Int = 1
    private var current = vector.start

    override fun hasNext() = current <= vector.endInclusive

    override fun next(): Point {
        val next = current
        current = current.next()
        return next
    }

    private fun Point.next() = when (vector) {
        is Horizontal -> Point(x, y + step)
        is Vertical -> Point(x + step, y)
        is Vector.Diagonal -> when (vector.start.y < vector.endInclusive.y) {
            true -> Point(x + step, y + step)
            false -> Point(x + step, y - step)
        }
    }
}

private fun String.asPoint() = split(",").let { (x, y) -> Point(x.toInt(), y.toInt()) }

