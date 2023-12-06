import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.system.measureTimeMillis

class Race(private val time: Long, private val record: Long) {
    fun count(): Long {
        val distance = record + 1 // minimum distance to beat the record
        val quadratic = sqrt(time.toDouble().pow(2) - (4 * distance))
        val x1 = ceil((time - quadratic)/2)
        val x2 = floor((time + quadratic)/2)
        return (x2 - x1 + 1).toLong()
    }
}

fun main() {
    fun part1(input: List<String>): Long {
        val times: List<Long> = parseLongLine(input[0].substring(5))
        val records: List<Long> = parseLongLine(input[1].substring(9))
        var total = 1L

        for (i in times.indices) {
            total *= Race(times[i], records[i]).count()
        }
        return total
    }

    fun part2(input: List<String>): Long {
        val time: Long = input[0].substring(5).replace(" ", "").toLong()
        val record: Long = input[1].substring(9).replace(" ", "").toLong()
        return Race(time, record).count()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288L)
    check(part2(testInput) == 71503L)

    val elapsed = measureTimeMillis {
        val input = readInput("Day06")
        part1(input).println()
        part2(input).println()
    }
    println("Completed in $elapsed milliseconds")
}
