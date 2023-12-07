enum class HandType(val score: Int) {
    FiveOfAKind(6),
    FourOfAKind(5),
    FullHouse(4),
    ThreeOfAKind(3),
    TwoPair(2),
    TwoOfAKind(1),
    HighCard(0)
}
class PokerHand(input: String, part: Int) {
    val bid: Int
    val cards: String
    val type: HandType

    init {
        val firstSplit = input.split(" ");
        cards = firstSplit[0]
        bid = Integer.parseInt(firstSplit[1])
        type = type(part)
    }

    private fun buildCardMap(): Map<Char, Int> {
        val map: MutableMap<Char, Int> = mutableMapOf()
        cards.forEach { card ->
            map[card] = map[card]?.plus(1) ?: 1
        }
        return map
    }

    private fun buildCardMap2(): Map<Char, Int> {
        val map: MutableMap<Char, Int> = mutableMapOf('J' to 0)
        var j = 0
        cards.forEach { card ->
            if (card == 'J') {
                j++
            } else {
                map[card] = map[card]?.plus(1) ?: 1
            }
        }
        val entry = map.maxBy { it.value }.key
        map[entry] = (map[entry] ?: 0) +  j
        return map
    }

    private fun type(part: Int): HandType {
        val map = if (part == 1) buildCardMap() else (buildCardMap2())

        return when(map.values.max()) {
            5 -> HandType.FiveOfAKind
            4 -> HandType.FourOfAKind
            3 -> {
                if (map.values.contains(2)) HandType.FullHouse else HandType.ThreeOfAKind
            }
            2 -> {
                if (map.values.filter { it == 2 }.size > 1) HandType.TwoPair else HandType.TwoOfAKind
            }
            else -> HandType.HighCard
        }
    }
}

class HandComparator {
    companion object : Comparator<PokerHand> {
        private val cardToScore: Map<Char, Int> = mapOf(
                'A' to 14,
                'K' to 13,
                'Q' to 12,
                'J' to 1,
                'T' to 10,
                '9' to 9,
                '8' to 8,
                '7' to 7,
                '6' to 6,
                '5' to 5,
                '4' to 4,
                '3' to 3,
                '2' to 2
        )
        override fun compare(a: PokerHand, b: PokerHand): Int {
            if (a.type != b.type) {
                return a.type.score - b.type.score
            }

            for (i in a.cards.indices) {
                val aScore = cardToScore[a.cards[i]]!!
                val bScore = cardToScore[b.cards[i]]!!
                if (aScore != bScore) {
                    return aScore - bScore
                }
            }
            return 0
        }
    }
}

fun main() {
    fun calculate(hands: List<PokerHand>): Int {
        var multiplier = 1
        var sum = 0
        for (hand in hands) {
            sum += hand.bid * multiplier
            multiplier++
        }
        return sum
    }

    fun part1(input: List<String>): Int {
        val result = input.map { line -> PokerHand(line, 1) }.sortedWith(HandComparator)
        return calculate(result)
    }

    fun part2(input: List<String>): Int {
        val result = input.map { line -> PokerHand(line, 2) }.sortedWith(HandComparator)
        return calculate(result)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 6440)
    check(part2(testInput) == 5905)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
