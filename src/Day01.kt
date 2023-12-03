fun main() {
    val matchers = mapOf(
            "one" to "1",
            "two" to "2",
            "three" to "3",
            "four" to "4",
            "five" to "5",
            "six" to "6",
            "seven" to "7",
            "eight" to "8",
            "nine" to "9"
    )

    fun convertToDigits(str: String): String {
        val regex = Regex("one|two|three|four|five|six|seven|eight|nine|[1-9]" )
        val matchResult = regex.findAll(str).map { res ->
            if (matchers.containsKey(res.value)) {
                matchers[res.value]
            } else {
                res.value
            }
        }
        return matchResult.first() + matchResult.last()
    }

    fun part1(input: List<String>): Int {
        var sum = 0
        input.forEach { str ->
            val digits = str.filter { it.isDigit() }
            val value = StringBuilder().append(digits.first()).append(digits.last()).toString()
            sum += Integer.parseInt(value)
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        input.forEach { str ->
            val foo = convertToDigits(str)
            sum += Integer.parseInt(foo)
        }
        return sum
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part2(testInput) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
