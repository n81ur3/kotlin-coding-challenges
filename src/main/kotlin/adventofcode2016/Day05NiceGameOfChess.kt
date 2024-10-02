package adventofcode2016

import java.math.BigInteger
import java.security.MessageDigest

class Md5Station {

    fun findPassword(doorID: String): String {
        val password = StringBuilder()
        var counter = 0
        var hash = ""
        while (password.length < 8) {
            hash = getHash(doorID + counter)
            if (hash.startsWith("00000")) {
                password.append(hash[5])
            }
            counter++
        }
        return password.toString()
    }

    fun findBetterPassword(doorID: String): String {
        val password = mutableListOf('_', '_', '_', '_', '_', '_', '_', '_')
        var counter = 0
        var hash = ""
        while (password.contains('_')) {
            hash = getHash(doorID + counter)
            if (hash.startsWith("00000")) {
                if (hash[5] in '0'..'7') {
                    val position = hash[5].digitToInt()
                    if (password[position] == '_') {
                        password[position] = hash[6]
                    }
                }
            }
            counter++
        }

        return password.joinToString(separator = "")
    }

    fun getHash(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }
}