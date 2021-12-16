package day13

import day13.Day13.part1
import day13.Day13.part2
import printResult
import readInput
import java.security.InvalidParameterException

object Day13 {

    fun part1(input: List<String>): Int {
        val dots = input.dots()
        val fold = input.folds().first()

        return dots.map { fold.fold(it) }.toSet().size
    }

    fun part2(input: List<String>) {
        val dots = input.dots()
        val folds = input.folds()

        val paper = folds.fold(dots) { paper, fold -> paper.map { fold.fold(it) }.toSet() }

        paper.print()
    }

    private fun Set<Point>.print() {
        (0..maxOf { it.y }).onEach { y ->
            (0..maxOf { it.x }).onEach { x ->
                if (Point(x, y) in this) print("#") else print(" ")
            }
            println()
        }
    }

    data class Point(val x: Int, val y: Int)

    sealed class Fold(val value: Int) {
        abstract fun fold(point: Point): Point

        class Up(value: Int) : Fold(value) {
            override fun fold(point: Point) =
                if (point.y < value) point else Point(point.x, value - (point.y - value))
        }

        class Left(value: Int) : Fold(value) {
            override fun fold(point: Point) =
                if (point.x < value) point else Point(value - (point.x - value), point.y)
        }

        companion object {
            operator fun invoke(type: Char, value: Int) = when (type.toString()) {
                "y" -> Up(value)
                "x" -> Left(value)
                else -> throw InvalidParameterException()
            }
        }
    }

    private fun List<String>.dots() = takeWhile { it.isNotBlank() }
        .map { it.split(",").let { (x, y) -> Point(x.toInt(), y.toInt()) } }
        .toSet()

    private fun List<String>.folds() = filter { it.startsWith("fold") }
        .map {
            it.substringAfter("fold along ")
                .split("=")
                .let { (type, value) -> Day13.Fold(type.single(), value.toInt()) }
        }
}

fun main() {
    val testInput = readInput("day13/Day13_test")
    with(part1(testInput)) { check(17 == this) { "result test 1: $this" } }

    val input = readInput("day13/Day13")
    printResult("Part 1") { part1(input) }
    printResult("Part 2") { part2(input) }
}
