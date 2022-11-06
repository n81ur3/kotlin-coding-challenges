package adventofcode2017

class Day24ElectromagneticMoat

data class Port(val sideA: Int, val sideB: Int) {
    val strength: Int
        get() = sideA + sideB
}

class Bridge(val ports: List<Port> = listOf()) {

    fun totalStrength(): Int = ports.sumOf { it.strength }

    override fun toString(): String {
        return buildString {
            append("[Strength: ${totalStrength()}]: ")
            ports.forEach {
                append("[")
                append(it.sideA)
                append("/")
                append(it.sideB)
                append("]")
                append(" - ")
            }
        }
    }
}

class BridgeBuilder(input: List<String>) {
    val constructedBridges = mutableSetOf<Bridge>()
    val strongestBridge: Int
        get() {
            return if (constructedBridges.isNotEmpty()) {
                constructedBridges.maxOf { it.totalStrength() }
            }
            else 0
        }
    val ports: List<Port>

    init {
        ports = input.map { portConfiguration ->
            val parts = portConfiguration.split("/")
            Port(parts[0].toInt(), parts[1].toInt())
        }
    }

    fun buildBridge(usedPorts: List<Port> = mutableListOf(), currentEnding: Int, remainingPorts: List<Port>) {
        val eligiblePorts = findMatchingPorts(currentEnding, remainingPorts)
        if (eligiblePorts.isNotEmpty()) {

            eligiblePorts.forEach { eligible ->
                val otherPorts = remainingPorts.filter { it != eligible }
                val otherEnd = if (eligible.sideA == currentEnding) eligible.sideB else eligible.sideA

                constructedBridges.add(Bridge(usedPorts + eligible))
                buildBridge(usedPorts + eligible, otherEnd, otherPorts)
            }
        } else {
            constructedBridges.add(Bridge(usedPorts))
        }
    }

    private fun findMatchingPorts(pins: Int, ports: List<Port>): List<Port> {
        return ports.filter { port -> port.sideA == pins || port.sideB == pins }
    }
}