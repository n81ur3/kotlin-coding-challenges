package adventofcode2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import utils.ResourceLoader
import java.io.File

class Day14SpaceStoichiometryTest {

    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2019/aoc2019_day14_input.txt")
    }

    @ParameterizedTest
    @MethodSource("sampleTestData1")
    fun runSamplesPart1(reactionsInput: List<String>, expectedResult: Long) {
        val chemicalReactor = ChemicalReactor(reactionsInput)

        val requiredOre = chemicalReactor.calculateRequiredOre()

        assertEquals(expectedResult, requiredOre)
    }

    @ParameterizedTest
    @MethodSource("sampleTestData2")
    fun runSamplesPart2(reactionsInput: List<String>, acquiredFire: Long) {
        val chemicalReactor = ChemicalReactor(reactionsInput)

        val bestMatch = chemicalReactor.calculateFireForOneTrillionOres()

        assertEquals(acquiredFire, bestMatch)
    }

    @Test
    fun solutionPart1() {
        val reactionsInput = file.readLines()
        val chemicalReactor = ChemicalReactor(reactionsInput)

        val requiredOre = chemicalReactor.calculateRequiredOre()

        assertEquals(483766, requiredOre)
        println("Solution for AoC2019-Day14-Part01: $requiredOre")
    }

    @Test
    fun solutionPart2() {
        val reactionsInput = file.readLines()
        val chemicalReactor = ChemicalReactor(reactionsInput)

        val bestMatch = chemicalReactor.calculateFireForOneTrillionOres()

        assertEquals(3061522, bestMatch)
        println("Solution for AoC2019-Day14-Part02: $bestMatch")
    }

companion object {
    @JvmStatic
    fun sampleTestData1() = listOf(
        Arguments.of(reactionsInput1, 31),
        Arguments.of(reactionsInput2, 165),
        Arguments.of(reactionsInput3, 13312),
        Arguments.of(reactionsInput4, 180697),
        Arguments.of(reactionsInput5, 2210736)
    )

    @JvmStatic
    fun sampleTestData2() = listOf(
        Arguments.of(reactionsInput3, 82892753L),
        Arguments.of(reactionsInput4, 5586022L),
        Arguments.of(reactionsInput5, 460664L)
    )

    val reactionsInput1 = listOf(
        "10 ORE => 10 A",
        "1 ORE => 1 B",
        "7 A, 1 B => 1 C",
        "7 A, 1 C => 1 D",
        "7 A, 1 D => 1 E",
        "7 A, 1 E => 1 FUEL"
    )

    val reactionsInput2 = listOf(
        "9 ORE => 2 A",
        "8 ORE => 3 B",
        "7 ORE => 5 C",
        "3 A, 4 B => 1 AB",
        "5 B, 7 C => 1 BC",
        "4 C, 1 A => 1 CA",
        "2 AB, 3 BC, 4 CA => 1 FUEL"
    )

        val reactionsInput3 = listOf(
            "157 ORE => 5 NZVS",
            "165 ORE => 6 DCFZ",
            "44 XJWVT, 5 KHKGT, 1 QDVJ, 29 NZVS, 9 GPVTF, 48 HKGWZ => 1 FUEL",
            "12 HKGWZ, 1 GPVTF, 8 PSHF => 9 QDVJ",
            "179 ORE => 7 PSHF",
            "177 ORE => 5 HKGWZ",
            "7 DCFZ, 7 PSHF => 2 XJWVT",
            "165 ORE => 2 GPVTF",
            "3 DCFZ, 7 NZVS, 5 HKGWZ, 10 PSHF => 8 KHKGT"
        )

        val reactionsInput4 = listOf(
            "2 VPVL, 7 FWMGM, 2 CXFTF, 11 MNCFX => 1 STKFG",
            "17 NVRVD, 3 JNWZP => 8 VPVL",
            "53 STKFG, 6 MNCFX, 46 VJHF, 81 HVMC, 68 CXFTF, 25 GNMV => 1 FUEL",
            "22 VJHF, 37 MNCFX => 5 FWMGM",
            "139 ORE => 4 NVRVD",
            "144 ORE => 7 JNWZP",
            "5 MNCFX, 7 RFSQX, 2 FWMGM, 2 VPVL, 19 CXFTF => 3 HVMC",
            "5 VJHF, 7 MNCFX, 9 VPVL, 37 CXFTF => 6 GNMV",
            "145 ORE => 6 MNCFX",
            "1 NVRVD => 8 CXFTF",
            "1 VJHF, 6 MNCFX => 4 RFSQX",
            "176 ORE => 6 VJHF"
        )

        val reactionsInput5 = listOf(
            "171 ORE => 8 CNZTR",
            "7 ZLQW, 3 BMBT, 9 XCVML, 26 XMNCP, 1 WPTQ, 2 MZWV, 1 RJRHP => 4 PLWSL",
            "114 ORE => 4 BHXH",
            "14 VRPVC => 6 BMBT",
            "6 BHXH, 18 KTJDG, 12 WPTQ, 7 PLWSL, 31 FHTLT, 37 ZDVW => 1 FUEL",
            "6 WPTQ, 2 BMBT, 8 ZLQW, 18 KTJDG, 1 XMNCP, 6 MZWV, 1 RJRHP => 6 FHTLT",
            "15 XDBXC, 2 LTCX, 1 VRPVC => 6 ZLQW",
            "13 WPTQ, 10 LTCX, 3 RJRHP, 14 XMNCP, 2 MZWV, 1 ZLQW => 1 ZDVW",
            "5 BMBT => 4 WPTQ",
            "189 ORE => 9 KTJDG",
            "1 MZWV, 17 XDBXC, 3 XCVML => 2 XMNCP",
            "12 VRPVC, 27 CNZTR => 2 XDBXC",
            "15 KTJDG, 12 BHXH => 5 XCVML",
            "3 BHXH, 2 VRPVC => 7 MZWV",
            "121 ORE => 7 VRPVC",
            "7 XCVML => 6 RJRHP",
            "5 BHXH, 4 VRPVC => 5 LTCX"
        )
    }
}

