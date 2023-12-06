class Parser(input: List<String>) {

    private val seeds: List<Long>
    private val seedRanges: MutableList<Range> = mutableListOf()
    private val allMaps: MutableList<AlmanacMap> = mutableListOf()

    private fun makeMap(input: List<String>, startingLine: String, endingLine: String = ""): AlmanacMap {
        val startIndex = input.indexOf(startingLine) + 1
        val endIndex = if (endingLine.isBlank()) input.size else input.indexOf(endingLine)
        return AlmanacMap(input.subList(startIndex, endIndex).filter { it.isNotBlank()} )
    }

    fun calculate(): Long {
        var minLocation = Long.MAX_VALUE
        for (seed in seeds) {
            var output = seed
            for (map in allMaps) {
                output = map.calculate(output)
            }
            if (output < minLocation) {
                minLocation = output
            }
        }
        return minLocation
    }

    fun calculateReversed(): Long {
        val startingRangeDestination = allMaps.last().ranges[0].destination // ranges are sorted by lowest destination
        val startingRange = Range(0, 0, startingRangeDestination)
        val rangeList = listOf(startingRange).union(allMaps.last().ranges)

        for (range in rangeList) { // sorted by lowest destination
            for (destination in range.destinationRange) {
                var input = destination
                for (map in allMaps.asReversed()) {
                    input = map.calculateReversed(input)
                }
                if (seedRanges.any { it.sourceRange.contains(input) }) {
                    return destination
                }
            }
        }
        throw Exception("cannot find minimum destination")
    }

    init {
        seeds = input[0]
                .substring(6)
                .split(" ")
                .filter { it.isNotBlank() }
                .map { it.toLong() }
        for (i in seeds.indices step 2) {
            seedRanges.add(Range(0, seeds[i], seeds[i + 1]))
        }
        val delimiters = listOf(
                "seed-to-soil map:",
                "soil-to-fertilizer map:",
                "fertilizer-to-water map:",
                "water-to-light map:",
                "light-to-temperature map:",
                "temperature-to-humidity map:",
                "humidity-to-location map:")
        allMaps.add(makeMap(input, delimiters[0], delimiters[1]))
        allMaps.add(makeMap(input, delimiters[1], delimiters[2]))
        allMaps.add(makeMap(input, delimiters[2], delimiters[3]))
        allMaps.add(makeMap(input, delimiters[3], delimiters[4]))
        allMaps.add(makeMap(input, delimiters[4], delimiters[5]))
        allMaps.add(makeMap(input, delimiters[5], delimiters[6]))
        allMaps.add(makeMap(input, delimiters[6]))
    }
}

class AlmanacMap {
    constructor(input: List<String>) {
        ranges = input.map { line ->
            val numericList = line.split(" ").map { it.toLong() }
            Range(numericList[0], numericList[1], numericList[2])
        }.sortedBy { it.destination }
    }

    val ranges: List<Range>

    fun calculate(source: Long): Long {
        for (range in ranges) {
            val result = range.mapSourceToDestination(source)
            if (result != null) {
                return result
            }
        }
        return source
    }

    fun calculateReversed(destination: Long): Long {
        for (range in ranges) {
            val result = range.mapDestinationToSource(destination)
            if (result != null) {
                return result
            }
        }
        return destination
    }
}

class Range(val destination: Long, val source: Long, length: Long) {
    val sourceRange = source until source + length
    val destinationRange = destination until destination + length
    fun mapSourceToDestination(x: Long): Long? {
       if (sourceRange.contains(x)) {
           val diff = x - source
           return destination + diff
       }
       return null
   }

    fun mapDestinationToSource(x: Long): Long? {
        if (destinationRange.contains(x)) {
            val diff = x - destination
            return source + diff
        }
        return null
    }
}

fun main() {
    fun part1(input: List<String>): Long {
        return Parser(input).calculate()
    }

    fun part2(input: List<String>): Long {
        return Parser(input).calculateReversed()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35L)
    check(part2(testInput) == 46L)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
