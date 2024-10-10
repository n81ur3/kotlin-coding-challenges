package adventofcode2016

class Unzipper {

    fun unzip(currentString: String): String {
        val nextMarkerStart = currentString.indexOf("(")
        if (nextMarkerStart == -1) return currentString

        val nextMarkerEnd = currentString.indexOfFirst { it == ')' }
        val markerLength = currentString.substring(nextMarkerStart + 1).takeWhile { it != 'x' }.toInt()
        val repeatCount = getRepeatCount(currentString.substring(nextMarkerStart, nextMarkerEnd + 1))
        val substitution = processMarker(
            currentString.substring(nextMarkerEnd + 1, nextMarkerEnd + markerLength + 1),
            repeatCount
        )

        return currentString.substring(0, nextMarkerStart) + substitution + unzip(currentString.substring(nextMarkerEnd + markerLength + 1))
    }

    private fun getRepeatCount(command: String): Int {
        return command.substringAfter("x").substringBefore(")").toInt()
    }

    private fun processMarker(subsequence: String, repeatCount: Int): String {
        return subsequence.repeat(repeatCount)
    }
}