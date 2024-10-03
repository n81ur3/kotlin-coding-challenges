package adventofcode2016

class IpV7router {

    fun isTLSsupported(ipAddress: String): Boolean {
        val hypernetRegex = "\\[(.*?)]".toRegex()
        val hypernets = hypernetRegex.findAll(ipAddress)

        var cleanedSequence = ipAddress

        hypernets.forEach { hypernet ->
            cleanedSequence = cleanedSequence.replace(hypernet.value, "|")
            hypernet.value.windowed(4, 1).forEach { subsequence ->
                if (isAbba(subsequence)) return false
            }

            if (isAbba(hypernet.value.substring(1, 5))) return false
        }

        cleanedSequence.split("|").forEach { partialAddress ->
            partialAddress.windowed(4, 1, partialWindows = true).forEach { subsequence ->
                if (isAbba(subsequence)) return true
            }
        }


        return false
    }

    fun isAbba(sequence: String): Boolean {
        if (sequence.length != 4) return false
        return (sequence[0] != sequence[1]) && (sequence[0] == sequence[3]) && (sequence[1] == sequence[2])
    }
}