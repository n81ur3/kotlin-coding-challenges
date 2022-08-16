package adventofcode2020

class Day25ComboBreaker {
    companion object {
        const val MODULUS = 20201227
        const val INITIAL_SUBJECT_NUMBER: Long = 7
    }

    fun calculatePublicKeyFromLoopSize(loops: Int): Long {
        return transform(loops)
    }

    fun transform(loopSize: Int, subject: Long = INITIAL_SUBJECT_NUMBER): Long {
        var result = 1L
        repeat(loopSize) {
            result = result * subject
            result = result % MODULUS
        }
        return result
    }

    fun calculateLoopSizesFromPublicKey(key: Long): Int {
        var result = 9374300
        generateSequence(1L) {
            ++result
            transform(result)
        }.first { it == key }
        return result
    }

    fun calculateEncryptionKey(publicKey: Long, loops: Int): Long {
        return transform(loops, publicKey)
    }
}