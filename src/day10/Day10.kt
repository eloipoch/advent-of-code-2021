package day10

import day10.Day10.part1
import day10.Day10.part2
import printResult
import readInput

object Day10 {
    fun part1(input: List<String>) = input.map(String::toCharArray).mapNotNull(::corrupted).map {
        when (it) {
            ")".single() -> 3
            "]".single() -> 57
            "}".single() -> 1197
            ">".single() -> 25137
            else -> 0 //remove not possible case
        }
    }.sum()

    fun part2(input: List<String>): Long {
        val test = input.map(String::toCharArray).filter { corrupted(it) == null }
            .map(::opened)
            .map { it.map(::expectedClosing).reversed() }
            .map {
                it.fold(0L) { total, char ->
                    val value = when (char) {
                        ")".single() -> 1
                        "]".single() -> 2
                        "}".single() -> 3
                        ">".single() -> 4
                        else -> 0 //remove not possible case
                    }

                    ((total * 5) + value).toLong()
                }
            }

        return test.sorted()[test.size / 2]
    }

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

    private fun opened(line: CharArray, opened: CharArray = charArrayOf()): CharArray {
        val current = line.firstOrNull() ?: return opened

        return if (opening(current)) {
            opened(line.drop(1).toCharArray(), opened + current)
        } else {
            if (current == expectedClosing(opened.last())) {
                opened(line.drop(1).toCharArray(), opened.dropLast(1).toCharArray())
            } else {
                opened
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
}

fun main() {
    val testInput = readInput("day10/Day10_test")
    with(part1(testInput)) { check(26397 == this) { "result test 1: $this" } }
    with(part2(testInput)) { check(288957L == this) { "result test 2: $this" } }

    val input = readInput("day10/Day10")
    printResult("Part 1") { part1(input) }
    printResult("Part 2") { part2(input) }
}
