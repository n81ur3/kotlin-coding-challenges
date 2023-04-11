package adventofcode2018

import adventofcode2018.BattleGroupType.*

class Day24ImmuneSystemSimulator20XX

enum class BattleGroupType {
    IMMUNESYSTEM, INFECTION
}

abstract class BattleGroup(
    val type: BattleGroupType,
    var units: Int,
    var hitPoints: Int,
    val weaks: List<String> = emptyList(),
    val immunes: List<String> = emptyList(),
    val attackDamage: Int,
    val attackType: String,
    val initiative: Int
) {
    val effectivePower: Int
        get() = units * attackDamage

    fun defendAgainst(otherGroup: BattleGroup, isSimulation: Boolean = true): Int {
        val damage = if (immunes.contains(otherGroup.attackType)) 0
        else if (weaks.contains(otherGroup.attackType)) otherGroup.effectivePower * 2
        else otherGroup.effectivePower
        if (!isSimulation) {
            units -= (damage / hitPoints)
        }
        return damage
    }

    override fun toString(): String {
        return "BattleGroup(type=$type, units=$units, hitPoints=$hitPoints, weaks=$weaks, immunes=$immunes, attackDamage=$attackDamage, attackType='$attackType', initiative=$initiative)"
    }


    companion object {
        fun fromInput(input: String, type: BattleGroupType): BattleGroup {
            val units = input.substringBefore(" units").toInt()
            val hitPoints = input.substringAfter("with ").substringBefore(" hit").toInt()
            var weaks: List<String> = emptyList()
            var immunes: List<String> = emptyList()
            val attackDamage = input.substringAfter("does ").substringBefore(" ").toInt()
            val attackType = input.substringBefore(" damage").substringAfterLast(" ")
            val initiative = input.substringAfterLast(" ").toInt()

            if (input.contains("weak")) {
                weaks = parseWeaks(input.substringAfter("(").substringBefore(")"), type)
            }
            if (input.contains("immune")) {
                immunes = parseImmunes(input.substringAfter("(").substringBefore(")"), type)
            }

            return when (type) {
                IMMUNESYSTEM -> ImmuneSystem(units, hitPoints, weaks, immunes, attackDamage, attackType, initiative)
                else -> Infection(units, hitPoints, weaks, immunes, attackDamage, attackType, initiative)
            }
        }

        private fun parseWeaks(input: String, type: BattleGroupType): List<String> {
            val parts = input.split("; ")
            return parts.filter { it.startsWith("weak ") }.first().substringAfter("weak to ").split(", ")
        }

        private fun parseImmunes(input: String, type: BattleGroupType): List<String> {
            val parts = input.split("; ")
            return parts.filter { it.startsWith("immune ") }.first().substringAfter("immune to ").split(", ")
        }
    }
}

object BattleGroupComparator : Comparator<BattleGroup> {
    override fun compare(group1: BattleGroup?, group2: BattleGroup?): Int {
        requireNotNull(group1)
        requireNotNull(group2)

        if (group1.effectivePower != group2.effectivePower) {
            return group1.effectivePower.compareTo(group2.effectivePower)
        } else if (group1.initiative != group2.initiative) {
            return group1.initiative.compareTo(group2.initiative)
        } else {
            return (group2.defendAgainst(group1)).compareTo(group1.defendAgainst(group2))
        }
    }
}

class ImmuneSystem(
    units: Int,
    hitPoints: Int,
    weaks: List<String> = emptyList(),
    immunes: List<String> = emptyList(),
    attackDamage: Int,
    attackType: String,
    initiative: Int
) : BattleGroup(
    type = IMMUNESYSTEM,
    units = units,
    hitPoints = hitPoints,
    weaks = weaks,
    immunes = immunes,
    attackDamage = attackDamage,
    attackType = attackType,
    initiative = initiative
)

class Infection(
    units: Int,
    hitPoints: Int,
    weaks: List<String> = emptyList(),
    immunes: List<String> = emptyList(),
    attackDamage: Int,
    attackType: String,
    initiative: Int
) : BattleGroup(
    type = INFECTION,
    units = units,
    hitPoints = hitPoints,
    weaks = weaks,
    immunes = immunes,
    attackDamage = attackDamage,
    attackType = attackType,
    initiative = initiative
)

class ImmuneSystemSimulator(input: List<String>) {
    val battleGroups: MutableList<BattleGroup>
    val immuneGroups: MutableList<BattleGroup>
    val infectionGroups: MutableList<BattleGroup>

    init {
        val groups = mutableListOf<BattleGroup>()
        var currentType = IMMUNESYSTEM
        input.drop(1).forEach {
            if (it == "Infection:") {
                currentType = INFECTION
            } else if (it.isNotEmpty()) {
                groups.add(BattleGroup.fromInput(it, currentType))
            }
        }
        battleGroups = groups
        immuneGroups = groups.filter { it.type == IMMUNESYSTEM }.toMutableList()
        infectionGroups = groups.filter { it.type == INFECTION }.toMutableList()
    }

    fun battle(): Int {

        while (battleGroups.groupBy { it.type }.keys.size > 1) {
            val battlePairs = targetSelection()

            battlePairs.forEach { (attacking, target) ->
                if (attacking.units > 0) {
                    target.defendAgainst(attacking, isSimulation = false)
                    if (target.units <= 0) {
                        battleGroups.remove(target)
                    }
                }
            }
        }

        return battleGroups.sumOf { it.units }
    }

    private fun targetSelection(): List<Pair<BattleGroup, BattleGroup>> {
        val result = mutableListOf<Pair<BattleGroup, BattleGroup>>()
        val immuneSystems = battleGroups.filter { it.type == IMMUNESYSTEM }
            .sortedWith(compareByDescending<BattleGroup> { it.effectivePower }.thenByDescending { it.initiative })
        val infections = battleGroups.filter { it.type == INFECTION }
            .sortedWith(compareByDescending<BattleGroup> { it.effectivePower }.thenByDescending { it.initiative })

        val selectedTargets = mutableListOf<BattleGroup>()

        immuneSystems.forEach { attackingGroup ->
            val target = selectTarget(attackingGroup, selectedTargets)
            target?.let {
                if (target.defendAgainst(attackingGroup) > 0) {
                    selectedTargets.add(target)
                    result.add(attackingGroup to target)
                }
            }
        }
        infections.forEach { attackingGroup ->
            val target = selectTarget(attackingGroup, selectedTargets)
            target?.let {
                if (target.defendAgainst(attackingGroup) > 0) {
                    selectedTargets.add(target)
                    result.add(attackingGroup to target)
                }
            }
        }

        return result.sortedByDescending { it.first.initiative }
    }

    private fun selectTarget(attackingGroup: BattleGroup, selectedTargets: List<BattleGroup>): BattleGroup? {
        return battleGroups.filterNot { it in selectedTargets }.filter { it.type != attackingGroup.type }
            .sortedWith(compareByDescending<BattleGroup> { it.defendAgainst(attackingGroup) }
                .thenByDescending { it.effectivePower }
                .thenByDescending { it.initiative })
            .firstOrNull()
    }
}