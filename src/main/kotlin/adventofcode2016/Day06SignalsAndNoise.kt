package adventofcode2016

class Day06SignalsAndNoise {
}

class NoiseDecoder {
    val buckets = mutableListOf<MutableList<Char>>()

    fun decodeMessage(messages: List<String>): String {
        parseInput(messages)
        return buildMessage()
    }

    private fun parseInput(messages: List<String>) {
        (0..messages.first().length).forEach { buckets.add(mutableListOf<Char>())}
        messages.forEach { message ->
            message.forEachIndexed { index, c -> buckets[index].add(c) }
        }
    }

    private fun buildMessage(): String {
        val message = StringBuilder()
        buckets.forEach { bucket ->
            val mostFrequentCharacter = bucket.groupBy { it }.maxByOrNull { it.value.size }?.value?.first() ?: ""
            message.append(mostFrequentCharacter)
        }
        return message.toString()
    }
}