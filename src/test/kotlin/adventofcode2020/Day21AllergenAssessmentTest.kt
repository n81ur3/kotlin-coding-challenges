package adventofcode2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day21AllergenAssessmentTest {
    lateinit var file: File

    val allAllergens = setOf(
        "fish",
        "soy",
        "dairy",
        "shellfish",
        "nuts",
        "wheat",
        "sesame",
        "peanuts"
    )

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2020/day21_input.txt")
    }

    @Test
    fun buildGrocery() {
        val lines = file.readLines()
        val grocery = Grocery.buildGroceryFromStrings(lines)
        assertEquals(37, grocery.foods.size)
    }

    private val strings: Set<String>
        get() {
            val allAllergens = setOf(
                "fish",
                "soy",
                "dairy",
                "shellfish",
                "nuts",
                "wheat",
                "sesame",
                "peanuts"
            )
            return allAllergens
        }

    @Test
    fun retrieveAllAllergens() {
        val lines = file.readLines()
        val grocery = Grocery.buildGroceryFromStrings(lines)
        val allergens = grocery.allAllergens()

        assertTrue(
            allergens.containsAll(
                allAllergens
            )
        )
    }

    @Test
    fun setIntersection() {
        val set1 = setOf("aa", "bb", "cc", "dd", "ee")
        val set2 = setOf("zz", "yy", "cc", "uu", "bb")

        println(set1.intersect(set2))
        assertTrue(set1.intersect(set2).containsAll(setOf("bb", "cc")))
    }

    @Test
    fun searchGroceryForFish() {
        val lines = file.readLines()
        val grocery = Grocery.buildGroceryFromStrings(lines)
        println(grocery.foodsContainingAllergen("fish"))
    }

    @Test
    fun searchGroceryForAllAllergens() {
        val lines = file.readLines()
        val grocery = Grocery.buildGroceryFromStrings(lines)
        allAllergens.forEach { allergen ->
            println("$allergen in: ${grocery.foodsContainingAllergen(allergen)}")
        }

        /**
         * fish in: [cxk]
         * soy in: [cfnt]
         * dairy in: [lmzg]
         * shellfish in: [drbm]
         * nuts in: [bsqh]
         * wheat in: [kqprv]
         * sesame in: [cpbzbx]
         * peanuts in: [bdvmx]
         */
    }

    @Test
    fun allFoodsContainingAllergens() {
        val lines = file.readLines()
        val grocery = Grocery.buildGroceryFromStrings(lines)
        val ingredients = grocery.ingredientsWithAllergen()
        assertTrue(ingredients.containsAll(setOf("cxk", "cfnt", "lmzg", "drbm", "bsqh", "kqprv", "cpbzbx", "bdvmx")))
    }

    @Test
    fun solutionDay21Part01() {
        val lines = file.readLines()
        val grocery = Grocery.buildGroceryFromStrings(lines)
        println("Number of ingredients without allergens: ${grocery.allIngredientsWithoutAllergens().size}")
    }

    @Test
    fun solutionDay21Part02() {
        val foodsWithAllergen = mapOf(
            "fish" to "cxk",
            "soy" to "cfnt",
            "dairy" to "lmzg",
            "shellfish" to "drbm",
            "nuts" to "bsqh",
            "wheat" to "kqprv",
            "sesame" to "cpbzbx",
            "peanuts" to "bdvmx"
        )
        val sortedFoods = foodsWithAllergen.toSortedMap()
        println("Solution day 21 part 02: ${sortedFoods.values.joinToString().replace(" ", "")}")
    }
}
