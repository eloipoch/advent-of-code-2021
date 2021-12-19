import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

/**
 * Print the result of the block next line after the title
 */
fun printResult(title: String, block: () -> Any) {
    println("Result $title")
    println(block())
}

fun List<String>.toInts() = map { it.toInt() }
fun String.splitToInts(delimiters: String = ",") = this.splitTo(delimiters, String::toInt)
fun <R> String.splitTo(delimiters: String = ",", to: (String) -> R) = this.split(delimiters).map(to)
fun List<Int>.countBy() = groupingBy { it }.eachCount()

/**
 * Returns the triangular number (https://en.wikipedia.org/wiki/Triangular_number)
 * Exmaples: 1=>1 2=>3 3=>6 4=>10 5=>15
 */
fun Int.triangular() = (this * (this + 1)) / 2

fun <T, K> Grouping<T, K>.eachSumBy(keySelector: (T) -> Long): Map<K, Long> =
    this.aggregate { _, accumulator, element, _ ->
        when (accumulator) {
            null -> keySelector(element)
            else -> keySelector(element) + accumulator
        }
    }

fun <T> T.repeat(amount: Int = 1, block: (T) -> T): T = when (amount) {
    1 -> block(this)
    else -> block(this).repeat(amount - 1, block)
}

data class Position(val x: Int, val y: Int) {
    private fun top() = Position(x, y - 1)
    private fun topRight() = Position(x + 1, y - 1)
    private fun right() = Position(x + 1, y)
    private fun bottomRight() = Position(x + 1, y + 1)
    private fun bottom() = Position(x, y + 1)
    private fun bottomLeft() = Position(x - 1, y + 1)
    private fun left() = Position(x - 1, y)
    private fun topLeft() = Position(x - 1, y - 1)

    private fun orthogonallyAdjacent() = listOf(top(), right(), bottom(), left())
    private fun diagonallyAdjacent() = listOf(topRight(), bottomRight(), bottomLeft(), topLeft())
    fun adjacent() = orthogonallyAdjacent() + diagonallyAdjacent()

}

fun <T> List<List<T>>.adjacent(position: Position): List<T> =
    position.adjacent().mapNotNull { getOrNull(it.y)?.getOrNull(it.x) }
