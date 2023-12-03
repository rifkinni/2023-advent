import javax.print.attribute.IntegerSyntax

class Game {
    constructor(str: String) {
        val initial = str.split(":")
        id = Integer.parseInt(Regex("[0-9]+").find(initial[0])!!.value)
        rounds = initial[1].split(";").map { Round(it) }
    }

    val id: Int
    val rounds: List<Round>

    private val diceCount = mapOf("red" to 12, "green" to 13, "blue" to 14)

    fun isValid(): Boolean {
        return rounds.none { !it.isValid(diceCount) }
    }

    fun power(): Int {
        val red: Int = max("red")
        val blue: Int = max("blue")
        val green: Int = max("green")
        return red * blue * green
    }

    private fun max(color: String): Int {
        return rounds.map {
            it.results[color]
        }.maxBy { it ?: 0 } ?: 0
    }
}

class Round {
    constructor(input: String) {
        val colorRegex = Regex("[a-z]+")
        val resultRegex = Regex("[0-9]+")
        input.split(",").forEach { str ->
            val color = colorRegex.find(str)!!.value
            val result = Integer.parseInt(resultRegex.find(str)!!.value)
            results[color] = result
        }
    }
    val results = mutableMapOf<String, Int>()

    fun isValid(diceCount: Map<String, Int>): Boolean {
        for (entry in diceCount) {
            if ((results[entry.key] ?: 0)  > entry.value) {
                return false
            }
        }
        return true
    }
}


fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0
        input.forEach { str ->
            val game = Game(str)
            if (game.isValid()) {
               sum += game.id
            }
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var power = 0
        input.forEach { str ->
            val game = Game(str)
            power += game.power()
        }
        return power
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
