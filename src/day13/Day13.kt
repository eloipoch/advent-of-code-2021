package day13

import day13.Day13.Fold.Left
import day13.Day13.Fold.Up
import day13.Day13.part1
import printResult
import readInput
import java.security.InvalidParameterException

object Day13 {
    data class Point(val x: Int, val y: Int) {
        override fun toString() = "$x,$y"
    }

    sealed class Fold(val value: Int) {
        abstract val type: Char

        class Up(value: Int) : Fold(value) {
            companion object {
                val TYPE = "y".single()
            }

            override val type = TYPE

            override fun toString() = "$type = $value"
        }

        class Left(value: Int) : Fold(value) {
            companion object {
                val TYPE = "x".single()
            }

            override val type = TYPE

            override fun toString() = "$type = $value"
        }

        companion object {
            operator fun invoke(type: Char, value: Int) = when (type) {
                Up.TYPE -> Up(value)
                Left.TYPE -> Left(value)
                else -> throw InvalidParameterException()
            }
        }
    }

    fun part1(input: List<String>): Int {
        val dots = input.takeWhile { it.isNotBlank() }
            .map { it.split(",").let { (x, y) -> Point(x.toInt(), y.toInt()) } }
            .toSet()

        val folds = input.filter { it.startsWith("fold") }
            .map {
                it.substringAfter("fold along ")
                    .split("=")
                    .let { (type, value) -> Fold(type.single(), value.toInt()) }
            }

        return when (val fold = folds.first()) {
            is Up -> {
                dots.mapNotNull {
                    when {
                        fold.value > it.y -> it
                        fold.value == it.y -> null
                        else -> Point(it.x, fold.value - (it.y - fold.value))
                    }
                }.toSet()
            }
            is Left -> {
                dots.mapNotNull {
                    when {
                        fold.value > it.x -> it
                        fold.value == it.x -> null
                        else -> Point(fold.value - (it.x - fold.value), it.y)
                    }
                }.toSet()
            }
        }.size
    }

    fun part2(input: List<String>): Int {
        return 1
    }
}

fun main() {
    val testInput = readInput("day13/Day13_test")
    with(part1(testInput)) { check(17 == this) { "result test 1: $this" } }
//    with(part2(testInput)) { check(? == this) { "result test 2: $this" } }

    val input = readInput("day13/Day13")
    printResult("Part 1") { part1(input) }
//    printResult("Part 2") { part2(input) }
}
