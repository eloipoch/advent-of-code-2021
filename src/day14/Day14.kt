package day14

import day14.Day14.part1
import printResult
import readInput

object Day14 {
    fun part1(input: List<String>): Int {
        val template = input.first().toList()
        val rules = input.drop(2).associate {
            val (pair, element) = it.split(" -> ")
            pair to element.single()
        }

        val result = rules.process(template, 10).groupingBy { it }.eachCount()

        return result.maxOf { it.value } - result.minOf { it.value }
    }

    private fun Map<String, Char>.process(template: List<Char>, amount: Int = 1): List<Char> = when (amount) {
        1 -> template.step(this)
        else -> process(template.step(this), amount - 1)
    }

    private fun List<Char>.step(rules: Map<String, Char>) = tail.fold(listOf(head)) { result, element ->
        val rule = charArrayOf(result.last(), element).concatToString()

        result + rules[rule]!! + element
    }

    private val List<Char>.head get() = first()
    private val List<Char>.tail get() = drop(1)

    fun part2(input: List<String>): Int {
        return 1
    }
}

fun main() {
    val testInput = readInput("day14/Day14_test")
    with(part1(testInput)) { check(1588 == this) { "result test 1: $this" } }
//    with(part2(testInput)) { check(? == this) { "result test 2: $this" } }

    val input = readInput("day14/Day14")
    printResult("Part 1") { part1(input) }
//    printResult("Part 2") { part2(input) }
}
