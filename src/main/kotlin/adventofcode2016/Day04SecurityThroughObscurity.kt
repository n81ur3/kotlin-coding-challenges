package adventofcode2016

class InfoKiosk() {

    fun checkRooms(roomDescriptions: List<String>) =
        roomDescriptions.map { Room.fromString(it) }.filter { it.verifyRoom() }.sumOf { it.sectorID }

    fun getNorthPoleObjectStorageID(roomDescriptions: List<String>): Int {
        val validRooms = roomDescriptions.map { Room.fromString(it) }.filter { it.verifyRoom() }
        return validRooms.first { it.decrypt() == "northpoleobjectstorage" }.sectorID
    }
}

data class Room(
    val name: String,
    val sectorID: Int,
    val checksum: String
) {

    fun verifyRoom(): Boolean {
        val charGroups = name.groupBy { it }
        val sortedGroup = charGroups.toSortedMap(compareByDescending<Char> { charGroups.get(it)?.size }.thenBy { it })
        val requiredChecksum = StringBuilder()
        sortedGroup.forEach { requiredChecksum.append(it.key) }
        return requiredChecksum.take(5).toString() == checksum
    }

    fun decrypt(): String {
        return name.map { if (it != '-') rotate(it) else ' '}.joinToString(separator = "")
    }

    fun rotate(c: Char): Char {
        val rotated = 97 + Math.floorMod(c.code + sectorID - 97, 26)
        val result = Char(rotated)
        return Char(rotated)
    }

    companion object {

        fun fromString(description: String): Room {
            val lastDashIndex = description.lastIndexOf('-')
            val firstDigitIndex = description.indexOfFirst { it.isDigit() }
            val openSquareBracketIndex = description.indexOf('[')
            val name = description.substring(0, lastDashIndex).replace("-", "")
            val sectorID = description.substring(firstDigitIndex, openSquareBracketIndex).toInt()
            val checksum = description.substringAfter('[').substringBefore(']')
            return Room(name, sectorID, checksum)
        }
    }
}