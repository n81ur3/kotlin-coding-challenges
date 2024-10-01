package adventofcode2016

import java.math.BigInteger
import java.security.MessageDigest

class Day05NiceGameOfChess {
}

class Md5Station {

    fun findPassword(doorID: String): String {
        var password = StringBuilder()
        var counter = 0
        var hash = ""
        while (password.length < 8) {
            hash = getHash("$doorID$counter")
            if (hash.startsWith("00000")) {
                password.append(hash[5])
            }
            counter++
        }
        return password.toString()
    }

    fun getHash(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }
}