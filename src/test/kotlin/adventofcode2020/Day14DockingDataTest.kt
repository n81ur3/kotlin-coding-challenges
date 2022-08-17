package adventofcode2020

import org.junit.Assert.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File
import kotlin.test.assertFailsWith

class Day14DockingDataTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2020/day14_input.txt")
    }

    @Test
    fun binaryFromStringToValue() {
        var initString = "0"
        var binary = Binary.fromString(initString)
        assertEquals(0L, binary.value)

        initString = "1"
        binary = Binary.fromString(initString)
        assertEquals(1L, binary.value)

        initString = "1000"
        binary = Binary.fromString(initString)
        assertEquals(8L, binary.value)

        initString = "111000100"
        binary = Binary.fromString(initString)
        assertEquals(452L, binary.value)

        initString = "01111111111111010000101110000110"
        binary = Binary.fromString(initString)
        assertEquals(2147289990L, binary.value)

        initString = "000000000000000000000000000001001001"
        binary = Binary.fromString(initString)
        assertEquals(73L, binary.value)
    }

    @Test
    fun convertToBinaryRepresentation() {
        var binaryString = "0"
        var binary = Binary.fromString(binaryString)
        assertEquals(binaryString, binary.asString)

        binaryString = "1"
        binary = Binary.fromString(binaryString)
        assertEquals(binaryString, binary.asString)

        binaryString = "111000100"
        binary = Binary.fromString(binaryString)
        assertEquals(binaryString, binary.asString)

        binaryString = "1111111111111010000101110000110"
        binary = Binary.fromString(binaryString)
        assertEquals(binaryString, binary.asString)
    }

    @Test
    fun negativeExponentThrowIllegalArgumentException() {
        assertFailsWith<IllegalArgumentException> {
            2L pow -1
        }
    }

    @Test
    fun setBitmaskOnBinary() {
        // @formatter:off
        var binaryString = "000000000000000000000000000000001011"
        var bitmask =      "XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X"
        var binary = Binary.fromString(binaryString)
        var result = binary.applyBitMask(bitmask)
        assertEquals(73L, result.value)

        binaryString = "001011110001001101100000010010001010"
        bitmask =      "X10X1XX0XXXX1XXX1XX1XXXX00XXXXXXX001"
        val expected = "010011100001101111110000000010001001"
        binary = Binary.fromString(binaryString)
        val expectedBinary = Binary.fromString(expected)
        result = binary.applyBitMask(bitmask)
        assertEquals(expectedBinary.value, result.value)
        // @formatter:on
    }

    @Test
    fun interpreterReadSampleLines() {
        val instruction = listOf(
            "mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
            "mem[37065] = 2066541",
            "mask = 111101XX101X1110110101X01X101X100X0X",
            "mem[17330] = 176272"
        )

        //      176272 = 000000000000000000101011000010010000
        //        mask = 111101XX101X1110110101X01X101X100X0X
        // 65681582752 = 111101001010111011010110101010100000
        // 2066541 + 65681582752 = 65683649293

        val interpreter = DockingDataInterpreter()
        instruction.forEach { interpreter.executeInstruction(it) }
        val memorySum = interpreter.calculateRamSum()

        println("Final sum: $memorySum")
        assertEquals(65683649293, memorySum)
    }

    @Test
    fun solutionDay14part1() {
        val lines = file.readLines()
        val interpreter = DockingDataInterpreter()
        lines.forEach { interpreter.executeInstruction(it) }

        println("Solution day14 part 1: memory sum = ${interpreter.calculateRamSum()}")
    }

    @Test
    fun permuteAddress() {
        var address = "00000000000000000000000000000001X0XX"
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

        addresses.forEach { println(it) }
    }

    @Test
    fun solutionDay14part2() {
        val lines = file.readLines()
        val interpreter = DockingDataInterpreter()
        lines.forEach { interpreter.executeInstruction(it) }

        println("Solution day14 part 2: memory sum = ${interpreter.calculateRamSum()}")
    }
}