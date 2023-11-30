package adventofcode2015

import java.math.BigInteger
import java.security.MessageDigest

class Day04IdealStockingStuffer

class MD5Breaker {

    fun findHash(secretKey: String, leadingZeroes: Int = 5): Int {
        var counter = 0
        var result: String
        while (true) {
            result = getMD5Hash(secretKey + counter.toString())
            if (result.startsWith("0".repeat(leadingZeroes))) return counter
            counter++
        }
    }

    fun getMD5Hash(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }
}