package day04

import printResult
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val numbers = input.first().split(",").map { it.toInt() }

        val boards = input.drop(1)
            .chunked(6)
            .map { it.drop(1) }
            .map { it.map { line -> line.trim().split("\\D+".toRegex()).map { number -> number.toInt() } } }

        val marked = List(boards.size) { List(10) { arrayOfNulls<Int>(5).toMutableList() } }

        val winner = numbers
            .asSequence()
            .takeWhile {
                marked.flatten().firstOrNull { it.filterNotNull().size == 5 } == null
            }
            .onEach { number ->
                boards.mapIndexed { index, board ->
                    val position = board.flatten().indexOf(number)
                    if (position != -1) {
                        marked[index][position / 5][position % 5] = number
                        marked[index][(position % 5) + 5][position / 5] = number
                    }
                }
            }.last()

        val winnerBoard = marked.indexOfFirst { board ->
            board.any { it.filterNotNull().size == 5 }
        }

        val sumUnmarked =
            boards[winnerBoard].flatten().sum() - marked[winnerBoard].take(5).flatten().filterNotNull().sum()

        return sumUnmarked * winner
    }


    fun part2(input: List<String>): Int {
        return 1
    }

    val testInput = readInput("day04/Day04_test")
    with(part1(testInput)) { check(4512 == this) { "result test 1: $this" } }
//    with(part2(testInput)) { check(? == this) { "result test 2: $this" } }

    val input = readInput("day04/Day04")
    printResult("Part 1") { part1(input) }
//    printResult("Part 2") { part2(input) }
}
