package adventofcode2020

import java.lang.Long.toBinaryString

class Day14DockingData

infix fun Long.pow(exponent: Int): Long {
    if (exponent < 0) throw IllegalArgumentException("Only exponents >= 0 allowed")
    if (exponent == 0) return 1L
    var result = this
    repeat(exponent - 1) {
        result *= this
    }
    return result
}

class Binary(var value: Long) {

    val asString: String
        get() = toBinaryString(value).padStart(36, '0')

    fun applyBitMask(mask: String): Binary {
        val result = asString.zip(mask) { v, b -> if (b == 'X') v else b }.joinToString(separator = "")
        return fromString(result)
    }

    fun applyMemoryAddressDecoder(mask: String): String {
        return asString.zip(mask) { v, b -> if (b == '0') v else b }.joinToString(separator = "")
    }

    companion object {
        fun fromString(initString: String): Binary {
            val value =
                initString.reversed().mapIndexedNotNull { index, char -> if (char == '1') 2L pow index else null }.sum()
            return Binary(value)
        }
    }
}

data class Ram(val storage: MutableMap<Long, Long>) {

    fun setValueAt(address: Long, value: Long) {
        storage[address] = value
    }

    fun getValueAt(address: Long): Long = storage[address] ?: 0L
}

class DockingDataInterpreter {
    var bitmask = "0".repeat(36)
    val ram = Ram(mutableMapOf())

    fun executeInstruction(input: String) {
        if (input.startsWith("mem")) executeMemoryInstruction(input)
        else if (input.startsWith("mask")) executeBitmaskInstruction(input)
    }

    // example: mem[37065] = 2066541
    fun executeMemoryInstruction(input: String) {
        val value = input.substringAfterLast(" ").toLong()
        val binaryAddress = Binary(input.substringAfter("[").substringBefore("]").toLong())
        val maskedAddress = binaryAddress.applyMemoryAddressDecoder(bitmask)
        var address = 0L
        for (ad in permuteAddress(maskedAddress)) {
            address = Binary.fromString(ad).value
            ram.setValueAt(address, value)
        }
    }


    // example: mem[37065] = 2066541
    fun parseMemoryInstruction(input: String) {
        val value = Binary(input.substringAfterLast(" ").toLong())
        val maskedBinary = value.applyBitMask(bitmask)
        val address = input.substringAfter("[").substringBefore("]").toLong()
        ram.setValueAt(address, maskedBinary.value)
    }

    // example: mask = 111101XX101X1110110101X01X101X100X0X
    fun executeBitmaskInstruction(input: String) {
        bitmask = input.substringAfterLast(" ")
    }

    fun permuteAddress(input: String): List<String> {
        var address = input
        val addresses = mutableListOf(address)
        val newAddresses = mutableListOf<String>()

        while (address.contains("X")) {
            newAddresses += addresses.map { it.replaceFirst("X", "0", false) }
            newAddresses += addresses.map { it.replaceFirst("X", "1", false) }
            addresses.clear()
            addresses.addAll(newAddresses)
            newAddresses.clear()
            address = address.replaceFirst("X", "_")
        }

        return addresses.toList()
    }

    fun calculateRamSum() = ram.storage.values.sum()
}
