import kotlin.math.pow

class Game04 {
    private val scratcherIdToCount: MutableMap<Int, Int> = mutableMapOf()

    fun addOrIncrement(id: Int) {
        scratcherIdToCount[id] = scratcherIdToCount[id]?.plus(1) ?: 1
    }

    fun playScratcher(scratcher: Scratcher) {
        addOrIncrement(scratcher.id)
        val winnings = scratcher.count()
        for (i in 0 until (scratcherIdToCount[scratcher.id] ?: 0)) {
            for (j in 1..winnings) {
                addOrIncrement(scratcher.id + j)
            }
        }
    }

    fun sum(): Int {
        return scratcherIdToCount.values.sum()
    }
}

class Scratcher {
    constructor(input: String) {
        val firstSplit = input.split(":")
        id = Integer.parseInt(firstSplit[0].substring(5).trim())
        val allNumbers = firstSplit[1].split("|")
        winningNumbers = cleanIntSet(allNumbers[0])
        ticketNumbers = cleanIntSet(allNumbers[1])

    }
    private val winningNumbers: Set<Int>
    private val ticketNumbers: Set<Int>
    val id: Int

    private fun cleanIntSet(str: String): Set<Int> {
        return str.trim()
                .split(" ")
                .filter { it.isNotBlank() }
                .map { Integer.parseInt(it) }
                .toSet()
    }

    fun count(): Int {
        return (winningNumbers intersect ticketNumbers).size
    }

    fun calculate(): Int {
        val overlap = count()
        return if (overlap == 0) 0 else 2.0.pow(overlap - 1).toInt()
    }

}
fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0
        input.forEach {
            sum += Scratcher(it).calculate()
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        val game = Game04()
        input.forEach {
            val scratcher = Scratcher(it)
            game.playScratcher(scratcher)
        }

        return game.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
