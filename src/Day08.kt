import kotlin.math.sqrt

class LocationMap(input: List<String>) {
    private val map: MutableMap<String, LeftRightPair> = mutableMapOf()
    var currentLocation = "AAA"
    val locations: List<String>

    init {
        for (entry in input) {
            val split = entry.split(" = ")
            add(split[0], split[1])
        }
        locations = map.keys.filter{ it[2] == 'A' }
    }

    fun doInstruction(instruction: Char) {
        currentLocation = if (instruction == 'L') {
            map[currentLocation]!!.left
        } else {
            map[currentLocation]!!.right
        }
    }

    private fun add(key: String, value: String) {
        map[key] = LeftRightPair(value)
    }
}

class LeftRightPair(input: String) {
    val left: String
    val right: String
    init {
        val firstSplit = input
                .replace("(", "")
                .replace(")","")
                .split(", ")
        left = firstSplit[0]
        right = firstSplit[1]
    }
}
fun main() {
    fun leastCommonMultiple(numbers: List<Long>): Long {
        if (numbers.size == 1) {
            return numbers[0]
        }

        val sorted = numbers.sortedDescending().toMutableList()
        val max = sorted.removeAt(0)
        val other = sorted.removeAt(0)
        var lcm = max

        while (lcm % other != 0L ) {
            lcm += max
        }
        sorted.add(lcm)
        return leastCommonMultiple(sorted)
    }

    fun part1(input: List<String>): Int {
        val instructions = input[0]
        val map = LocationMap(input.slice(2 until input.size))

        var counter = 0
        var index = 0
        while (map.currentLocation != "ZZZ") {
            map.doInstruction(instructions[index])
            counter++
            index = (index + 1) % instructions.length
        }

        return counter
    }

    fun part2(input: List<String>): Long {
        val instructions = input[0]
        val map = LocationMap(input.slice(2 until input.size))

        val results = mutableListOf<Long>()
        for (location in map.locations) {
            map.currentLocation = location

            var counter = 0L
            var index = 0
            while (map.currentLocation[2] != 'Z') {
                map.doInstruction(instructions[index])
                counter++
                index = (index + 1) % instructions.length
            }

            results.add(counter)
        }
        return leastCommonMultiple(results)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(leastCommonMultiple(listOf(2L, 3L)) == 6L)
    check(leastCommonMultiple(listOf(5L, 10L)) == 10L)
    check(leastCommonMultiple(listOf(12L, 15L, 10L, 5L)) == 60L)
    check(part2(testInput) == 6L)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
