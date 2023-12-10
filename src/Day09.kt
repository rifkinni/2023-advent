fun main() {
    fun buildDifferences(sequence: List<Int>): List<Int> {
        val differences = mutableListOf<Int>()
        for (idx in 0.. sequence.size - 2) {
            differences.add(sequence[idx + 1] - sequence[idx])
        }
        return differences
    }

    fun calculateDifferences(sequence: List<Int>): Int {
        if (sequence.all { it == 0 }) {
            return 0
        }
        val differences = buildDifferences(sequence)

        return sequence.last() + calculateDifferences(differences)
    }

    fun backwards(sequence: List<Int>): Int {
        if (sequence.all { it == 0 }) {
            return 0
        }
        val differences = buildDifferences(sequence)

        return sequence.first() - backwards(differences)
    }

    fun part1(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            sum += calculateDifferences(parseIntLine(line))
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            sum+= backwards(parseIntLine(line))
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 114)
    check(part2(testInput) == 2)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
