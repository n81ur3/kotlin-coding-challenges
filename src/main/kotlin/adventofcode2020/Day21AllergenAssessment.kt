package adventofcode2020

class Day21AllergenAssessment

data class Food(val ingredients: Set<String>, val allergens: Set<String>) {
    companion object {
        fun fromString(input: String): Food {
            val parts = input.split(" (contains ")
            val ing = parts[0].split(" ").toHashSet()
            val allg = parts[1].dropLast(1).split(" ").map { it.replace(",", "") }.toHashSet()
            return Food(ing, allg)
        }
    }
}

class Grocery(val foods: List<Food>) {

    companion object {
        fun buildGroceryFromStrings(lines: List<String>): Grocery {
            val goods = lines.map { Food.fromString(it) }
            return Grocery(goods)
        }
    }

    fun allAllergens(): Set<String> = foods.flatMap { food -> food.allergens }.toHashSet()

    fun foodsContainingAllergen(allergen: String): Set<String> {
        val foodCandidates = foods.filter { food -> food.allergens.contains(allergen) }
        val ingredientCandidates = foodCandidates.map { food -> food.ingredients }
        val intersection = ingredientCandidates.reduce { acc, current -> acc.intersect(current) }
        return intersection
    }

    fun ingredientsWithAllergen(): Set<String> {
        val candidates = allAllergens().map { allergen -> foodsContainingAllergen(allergen) }
        return candidates.reduce { acc, current -> acc + current }
    }

    fun allIngredients(): List<String> =
        foods.map { food -> food.ingredients.toList() }.reduce { acc, current -> acc + current }

    fun allIngredientsWithoutAllergens(): List<String> {
        val allIngredients = allIngredients()
        val ingredientsWithAllergens = ingredientsWithAllergen()
        return allIngredients.filter { ingredient -> ingredient !in ingredientsWithAllergens }
    }
}