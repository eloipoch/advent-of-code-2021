package day10

import day10.Day10.Character.Closing
import day10.Day10.Character.Closing.CloseBrackets
import day10.Day10.Character.Closing.CloseCurlyBrackets
import day10.Day10.Character.Closing.CloseParenthesis
import day10.Day10.Character.Closing.MoreThan
import day10.Day10.Character.Companion.character
import day10.Day10.Character.Opening
import day10.Day10.Character.Opening.LessThan
import day10.Day10.Character.Opening.OpenBrackets
import day10.Day10.Character.Opening.OpenCurlyBrackets
import day10.Day10.Character.Opening.OpenParenthesis
import day10.Day10.Line.Companion.line
import day10.Day10.Line.Corrupted
import day10.Day10.Line.Uncompleted
import day10.Day10.part1
import day10.Day10.part2
import printResult
import readInput
import java.security.InvalidParameterException

object Day10 {

    fun part1(input: List<String>) =
        input.map(::line).filterIsInstance<Corrupted>().sumOf(Corrupted::score)

    fun part2(input: List<String>) =
        input.map(::line).filterIsInstance<Uncompleted>().map(Uncompleted::score).winner()

    sealed class Character {
        sealed class Opening(val closing: Closing) : Character() {
            object OpenParenthesis : Opening(CloseParenthesis)
            object OpenBrackets : Opening(CloseBrackets)
            object OpenCurlyBrackets : Opening(CloseCurlyBrackets)
            object LessThan : Opening(MoreThan)
        }

        sealed class Closing : Character() {
            object CloseParenthesis : Closing()
            object CloseBrackets : Closing()
            object CloseCurlyBrackets : Closing()
            object MoreThan : Closing()
        }

        companion object {
            fun character(char: Char) = when (char.toString()) {
                "(" -> OpenParenthesis
                "[" -> OpenBrackets
                "{" -> OpenCurlyBrackets
                "<" -> LessThan
                ")" -> CloseParenthesis
                "]" -> CloseBrackets
                "}" -> CloseCurlyBrackets
                ">" -> MoreThan
                else -> throw InvalidParameterException()
            }
        }
    }

    sealed class Line {
        abstract fun score(): Long

        class Corrupted(private val illegal: Closing) : Line() {
            override fun score() = when (illegal) {
                CloseParenthesis -> 3
                CloseBrackets -> 57
                CloseCurlyBrackets -> 1197
                MoreThan -> 25137
            }.toLong()
        }

        class Uncompleted(private val closing: List<Closing>) : Line() {
            override fun score() = closing.fold(0L) { total, closing -> (total * 5) + closing.value() }

            private fun Closing.value() = when (this) {
                CloseParenthesis -> 1
                CloseBrackets -> 2
                CloseCurlyBrackets -> 3
                MoreThan -> 4
            }
        }

        companion object {
            fun line(line: String): Line {
                val chunks = line.map(::character)

                return when (val illegal = corrupted(chunks)) {
                    null -> Uncompleted(opened(chunks).map { (it).closing }.reversed())
                    else -> Corrupted(illegal)
                }
            }

            private fun corrupted(line: List<Character>, opened: List<Opening> = listOf()): Closing? {
                val current = line.firstOrNull() ?: return null

                return when (current) {
                    is Opening -> corrupted(line.drop(1), opened + current)
                    is Closing -> if (current == opened.last().closing) {
                        corrupted(line.drop(1), opened.dropLast(1))
                    } else {
                        current
                    }
                }
            }

            private fun opened(line: List<Character>, opened: List<Opening> = listOf()): List<Opening> {
                val current = line.firstOrNull() ?: return opened

                return when (current) {
                    is Opening -> opened(line.drop(1), opened + current)
                    is Closing -> if (current == opened.last().closing) {
                        opened(line.drop(1), opened.dropLast(1))
                    } else {
                        opened
                    }
                }
            }
        }
    }

    private fun List<Long>.winner() = sorted()[size / 2]
}


fun main() {
    val testInput = readInput("day10/Day10_test")
    with(part1(testInput)) { check(26397L == this) { "result test 1: $this" } }
    with(part2(testInput)) { check(288957L == this) { "result test 2: $this" } }

    val input = readInput("day10/Day10")
    printResult("Part 1") { part1(input) }
    printResult("Part 2") { part2(input) }
}
