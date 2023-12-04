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

    fun findMatch(str: String, pattern: String): String {
        val regex = Regex(pattern )
        return regex.find(str)!!.value
    }

    fun findFirst(str: String): String {
        val pattern = "one|two|three|four|five|six|seven|eight|nine|[1-9]"
        val result = findMatch(str, pattern)
        return matchers[result] ?: result
    }

    fun findLast(str: String): String {
        val pattern = "eno|owt|eerht|ruof|evif|xis|neves|thgie|enin|[1-9]"
        val result = findMatch(str.reversed(), pattern)
        return matchers[result.reversed()] ?: result
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
            val foo = findFirst(str) + findLast(str)
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
