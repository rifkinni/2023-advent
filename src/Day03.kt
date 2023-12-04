abstract class SchematicEntry(val x: Int, val y: Int) {
    abstract val length: Int
}

class Number(x: Int, y: Int, stringVal: String): SchematicEntry(x, y) {
    override val length = stringVal.length
    val value: Int = Integer.parseInt(stringVal)
    val xRange = x - 1..x + length
    val yRange = y -1 .. y + 1

    fun checkAdjacency(symbols: List<Symbol>): Boolean {
        for (symbol in symbols) {
            if (xRange.contains(symbol.x) && yRange.contains(symbol.y)) {
                return true
            }
        }
        return false
    }
}

class Symbol(x: Int,y: Int, val value: Char): SchematicEntry(x, y) {
    override val length = 1

    fun calculateGearRatio(numbers: List<Number>): Int {
        var count = 0
        var ratio = 1
        for (num in numbers) {
            if (num.xRange.contains(x) && num.yRange.contains(y)) {
                ratio *= num.value
                count ++
            }
        }
        return if (count == 2)  ratio else 0
    }
}

class Schematic {
    private val numbers: MutableList<Number> = mutableListOf()
    private val symbols: MutableList<Symbol> = mutableListOf()
    private val regex = Regex("[^\\.\\w]")
    private var sum = 0
    private var gearRatio = 0

    constructor(input: List<String>) {
        var numberBuilder = StringBuilder()
        for (y in input.indices) {
            val str = input[y]
            for (x in str.indices) {
                if (str[x].isDigit()) {
                    numberBuilder.append(str[x])
                } else {
                    if (numberBuilder.isNotBlank()) {
                        numbers.add(Number(x - numberBuilder.length, y, numberBuilder.toString()))
                        numberBuilder.clear()
                    }

                    if (regex.matches(str[x].toString())) {
                        symbols.add(Symbol(x, y, str[x]))
                    }
                }
            }
            if (numberBuilder.isNotBlank()) {
                val x = str.length - numberBuilder.length
                numbers.add(Number(x, y, numberBuilder.toString()))
                numberBuilder.clear()
            }
        }
    }

    fun findAdjacencySum(): Int {
        for (num in numbers) {
            if (num.checkAdjacency(symbols)) {
                sum += num.value
            }
        }
        return sum
    }

    fun findGearRatioSum(): Int {
        for (symbol in symbols) {
            if (symbol.value == '*') {
                gearRatio += symbol.calculateGearRatio(numbers)
            }
        }
        return gearRatio
    }
}


fun main() {
    fun part1(input: List<String>): Int {
        return Schematic(input).findAdjacencySum()
    }

    fun part2(input: List<String>): Int {
        return Schematic(input).findGearRatioSum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
