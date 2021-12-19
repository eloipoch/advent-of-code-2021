package day14

import day14.Day14.part1
import day14.Day14.part2
import printResult
import readInput
import kotlin.math.roundToLong

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

    data class InsertionPair(private val value: String) {
        val first get() = value[0]
        val second get() = value[1]
    }

    private fun String.toPair() = InsertionPair(this)

    class PolymerTemplate(private val value: List<Counter>) : List<Counter> by value {
        constructor(values: Map<InsertionPair, Long>) : this(values.map { Counter(it.key, it.value) })

        companion object {
            fun parse(string: String) = string.windowed(2).map(::InsertionPair)
                .groupingBy { it }.eachCount().mapValues { it.value.toLong() }
                .let(::PolymerTemplate)
        }
    }

    class Rules(private val rules: Map<InsertionPair, List<InsertionPair>>) :
        Map<InsertionPair, List<InsertionPair>> by rules {

        companion object {
            fun parse(rules: List<String>) = rules.associate {
                val (pair, element) = it.split(" -> ")
                pair.toPair() to listOf(InsertionPair(pair[0] + element), InsertionPair(element + pair[1]))
            }.let(::Rules)
        }
    }

    data class Counter(val pair: InsertionPair, val amount: Long)

    fun part2(input: List<String>): Long {
        val template = PolymerTemplate.parse(input.first())
        val rules = Rules.parse(input.drop(2))

        val final = (1..40).fold(template) { result, _ ->
            result
                .flatMap { (pair, count) -> rules.getValue(pair).map { Counter(it, count) } }
                .groupingBy(Counter::pair).eachSumBy { it.amount }
                .let(::PolymerTemplate)
        }

        val result = final
            .flatMap { (pair, count) -> listOf(pair.first to count, pair.second to count) }
            .groupingBy { it.first }.eachSumBy { it.second }
            .map { (it.value / 2.0).roundToLong() }

        return result.maxOf { it } - result.minOf { it }
    }

    private fun <T, K> Grouping<T, K>.eachSumBy(keySelector: (T) -> Long): Map<K, Long> =
        this.aggregate { _, accumulator, element, _ ->
            when (accumulator) {
                null -> keySelector(element)
                else -> keySelector(element) + accumulator
            }
        }
}

fun main() {
    val testInput = readInput("day14/Day14_test")
    with(part1(testInput)) { check(1588 == this) { "result test 1: $this" } }
    with(part2(testInput)) { check(2188189693529 == this) { "result test 2: $this" } }

    val input = readInput("day14/Day14")
    printResult("Part 1") { part1(input) }
    printResult("Part 2") { part2(input) }
}
