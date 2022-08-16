package adventofcode2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Day25ComboBreakerTest {
    lateinit var combo: Day25ComboBreaker

    @BeforeEach
    fun setup() {
        combo = Day25ComboBreaker()
    }

    @Test
    fun calculatePublicKeyForSamples() {
        assertEquals(5764801, combo.calculatePublicKeyFromLoopSize(8))
        assertEquals(17807724, combo.calculatePublicKeyFromLoopSize(11))
    }

    @Test
    fun calculateLoopSizesForSample() {
        val firstLoopSize = combo.calculateLoopSizesFromPublicKey(5764801)
        val secondLoopSize = combo.calculateLoopSizesFromPublicKey(17807724)
        assertEquals(8, firstLoopSize)
        assertEquals(11, secondLoopSize)
    }

    @Test
    fun calculateEncryptionKeyForSample() {
        assertEquals(14897079, combo.calculateEncryptionKey(17807724, 8))
        assertEquals(14897079, combo.calculateEncryptionKey(5764801, 11))
    }

    @Test
    fun simulateSample() {
        val firstLoopSize = combo.calculateLoopSizesFromPublicKey(5764801)
        val secondLoopSize = combo.calculateLoopSizesFromPublicKey(17807724)
        val encryptionKey = combo.calculateEncryptionKey(17807724, firstLoopSize)
        assertEquals(encryptionKey, combo.calculateEncryptionKey(5764801, secondLoopSize))
        println("Encryption key for sample: $encryptionKey")
    }

    @Test
    fun solutionDay25Part01() {
        val firstPublicKey = 18499292L
        val secondPublicKey = 8790390L
        val firstLoopSize = combo.calculateLoopSizesFromPublicKey(firstPublicKey)
        val encryptionKey = combo.calculateEncryptionKey(secondPublicKey, firstLoopSize)
        println("Solution day25 part01: Encryption Key = $encryptionKey")
    }
}