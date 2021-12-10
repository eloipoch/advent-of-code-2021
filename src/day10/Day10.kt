package day10

import day10.Day10.part1
import printResult
import readInput

object Day10 {
    fun part1(input: List<String>) = input.map(String::toCharArray).mapNotNull(::corrupted).map {
        when (it) {
            ")".single() -> 3
            "]".single() -> 57
            "}".single() -> 1197
            ">".single() -> 25137
            else -> 0 //remove not possible caseπ
        }
    }.sum()

    private fun corrupted(line: CharArray, opened: CharArray = charArrayOf()): Char? {
        val current = line.firstOrNull() ?: return null

        return if (opening(current)) {
            corrupted(line.drop(1).toCharArray(), opened + current)
        } else {
            if (current == expectedClosing(opened.last())) {
                corrupted(line.drop(1).toCharArray(), opened.dropLast(1).toCharArray())
            } else {
                current
            }
        }
    }

    private fun expectedClosing(opening: Char) = when (opening) {
        "(".single() -> ")".single()
        "[".single() -> "]".single()
        "{".single() -> "}".single()
        "<".single() -> ">".single()
        else -> "?".single() //remove not possible case
    }

    private fun opening(char: Char) = (char in listOf("(", "[", "{", "<").map(String::single))
    private fun closing(char: Char) = opening(char).not()

    fun part2(input: List<String>): Int {
        return 1
    }
}

fun main() {
    val testInput = readInput("day10/Day10_test")
    with(part1(testInput)) { check(26397 == this) { "result test 1: $this" } }
//    with(part2(testInput)) { check(? == this) { "result test 2: $this" } }

    val input = readInput("day10/Day10")
    printResult("Part 1") { part1(input) }
//    printResult("Part 2") { part2(input) }
}
