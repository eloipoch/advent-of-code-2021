package day14

import day14.Day14.part1
import day14.Day14.part2
import eachSumBy
import printResult
import readInput
import repeat
import kotlin.math.roundToLong

object Day14 {
    fun part1(input: List<String>): Long {
        val template = PolymerTemplate.parse(input.first())
        val rules = Rules.parse(input.drop(2))

        return template.process(rules, 10)
    }

    fun part2(input: List<String>): Long {
        val template = PolymerTemplate.parse(input.first())
        val rules = Rules.parse(input.drop(2))

        return template.process(rules, 40)
    }

    private fun PolymerTemplate.process(rules: Rules, times: Int) = repeat(times) { current ->
        current.flatMap { (pair, count) -> rules.getValue(pair).map { Counter(it, count) } }
            .groupingBy(Counter::pair).eachSumBy { it.amount }
            .let(Day14::PolymerTemplate)
    }.result()

    private fun PolymerTemplate.result() =
        flatMap { (pair, count) -> listOf(pair.first to count, pair.second to count) }
            .groupingBy { it.first }.eachSumBy { it.second }
            .map { (it.value / 2.0).roundToLong() }
            .let { values -> values.maxOf { it } - values.minOf { it } }

    data class InsertionPair(private val value: String) {
        val first get() = value[0]
        val second get() = value[1]
    }

    data class Counter(val pair: InsertionPair, val amount: Long)

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
                InsertionPair(pair) to listOf(InsertionPair(pair[0] + element), InsertionPair(element + pair[1]))
            }.let(::Rules)
        }
    }
}

fun main() {
    val testInput = readInput("day14/Day14_test")
    with(part1(testInput)) { check(1588L == this) { "result test 1: $this" } }
    with(part2(testInput)) { check(2188189693529 == this) { "result test 2: $this" } }

    val input = readInput("day14/Day14")
    printResult("Part 1") { part1(input) }
    printResult("Part 2") { part2(input) }
}
