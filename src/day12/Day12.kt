package day12

import day12.Day12.Cave.Companion.END
import day12.Day12.Cave.Companion.START
import day12.Day12.Cave.Small
import day12.Day12.part1
import printResult
import readInput

object Day12 {

    fun part1(input: List<String>) = CaveSystem.parse(input).existingPaths().size

    fun part2(input: List<String>): Int {
        return 1
    }

    sealed class Cave(open val name: String) {
        data class Small(override val name: String) : Cave(name)
        data class Big(override val name: String) : Cave(name)

        companion object {
            val START = Small("start")
            val END = Small("end")

            operator fun invoke(name: String) = if (name.all(Char::isLowerCase)) Small(name) else Big(name)
        }
    }

    data class Path(val from: Cave, val to: Cave) {
        fun connected(cave: Cave) = when (cave) {
            from -> to
            to -> from
            else -> null
        }
    }

    class CaveSystem(private val paths: List<Path>) : List<Path> by paths {
        fun existingPaths(
            current: Cave = START,
            visited: List<Cave> = listOf(current)
        ): List<List<Cave>> = when (current) {
            END -> listOf(visited)
            else -> mapNotNull { it.connected(current) }
                .filterNot { it is Small && it in visited }
                .flatMap { cave -> existingPaths(cave, visited + cave) }
        }

        companion object {
            fun parse(input: List<String>) = CaveSystem(input.map { string ->
                string.split("-").map { Cave(it) }.let { (from, to) -> Path(from, to) }
            })
        }
    }
}

fun main() {
    val testInput = readInput("day12/Day12_test")
    with(part1(testInput)) { check(226 == this) { "result test 1: $this" } }
//    with(part2(testInput)) { check(? == this) { "result test 2: $this" } }

    val input = readInput("day12/Day12")
    printResult("Part 1") { part1(input) }
//    printResult("Part 2") { part2(input) }
}

