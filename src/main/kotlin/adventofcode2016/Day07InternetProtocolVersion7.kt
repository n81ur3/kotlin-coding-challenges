package adventofcode2016

class IpV7router {

    fun isTLSsupported(ipAddress: String): Boolean {
        val hypernets = findHypernets(ipAddress)
        var baseAddress = ipAddress

        hypernets.forEach { hypernet ->
            baseAddress = baseAddress.replace(hypernet.value, "|")
            hypernet.value.windowed(4, 1).forEach { subsequence ->
                if (isAbba(subsequence)) return false
            }

            if (isAbba(hypernet.value.substring(1, 5))) return false
        }

        baseAddress.split("|").forEach { partialAddress ->
            partialAddress.windowed(4, 1, partialWindows = true).forEach { subsequence ->
                if (isAbba(subsequence)) return true
            }
        }

        return false
    }

    private fun findHypernets(ipAddress: String): Sequence<MatchResult> {
        val hypernetRegex = "\\[(.*?)]".toRegex()
        val hypernets = hypernetRegex.findAll(ipAddress)
        return hypernets
    }

    fun isSSLsupported(ipAddress: String): Boolean {
        val hypernets = findHypernets(ipAddress)
        var baseAddress = ipAddress

        hypernets.forEach { hypernet ->
            baseAddress = baseAddress.replace(hypernet.value, "|")
        }

        hypernets.forEach { hypernet ->
            hypernet.value.windowed(3, 1).forEach { subsequence ->
                if (isAbA(subsequence)) {
                    baseAddress.split("|").forEach {
                        it.windowed(3, 1).forEach { partialAddress ->
                            if (isBaB(partialAddress, subsequence)) {
                                return true
                            }
                        }
                    }
                }
            }
        }

        return false
    }

    fun isAbba(sequence: String): Boolean {
        if (sequence.length != 4) return false
        return (sequence[0] != sequence[1]) && (sequence[0] == sequence[3]) && (sequence[1] == sequence[2])
    }

    fun isAbA(sequence: String): Boolean {
        if (sequence.length != 3) return false
        return (sequence[0] != sequence[1]) && (sequence[0] == sequence[2])
    }

    fun isBaB(sequence: String, aba: String): Boolean {
        return (sequence[0] == aba[1]) && (sequence[1] == aba[0]) && (sequence[2] == aba[1])
    }
}