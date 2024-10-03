package adventofcode2016

class Day06SignalsAndNoise {
}

class NoiseDecoder {
    val buckets = mutableListOf<MutableList<Char>>()

    fun decodeMessage(messages: List<String>): String {
        parseInput(messages)
        return buildMessage(false)
    }

    fun decodeHiddenMessage(messages: List<String>): String {
        parseInput(messages)
        return buildMessage(true)
    }

    private fun parseInput(messages: List<String>) {
        (0..messages.first().length).forEach { buckets.add(mutableListOf<Char>())}
        messages.forEach { message ->
            message.forEachIndexed { index, c -> buckets[index].add(c) }
        }
    }

    private fun buildMessage(findLeastCommon: Boolean): String {
        val message = StringBuilder()
        buckets.forEach { bucket ->
            if (findLeastCommon) {
                val leastFrequentCharacter = bucket.groupBy { it }.minByOrNull { it.value.size }?.value?.first() ?: ""
                message.append(leastFrequentCharacter)
            } else {
                val mostFrequentCharacter = bucket.groupBy { it }.maxByOrNull { it.value.size }?.value?.first() ?: ""
                message.append(mostFrequentCharacter)
            }
        }
        return message.toString()
    }
}