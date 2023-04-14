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
    val initiative: Int,
    var booster: Int = 0
) {
    val effectivePower: Int
        get() = units * (attackDamage + booster)

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

    fun copy(): BattleGroup {
        return when (type) {
            IMMUNESYSTEM -> ImmuneSystem(units, hitPoints, weaks, immunes, attackDamage, attackType, initiative)
            INFECTION -> Infection(units, hitPoints, weaks, immunes, attackDamage, attackType, initiative)
        }
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
    var battleGroups: MutableList<BattleGroup>
    var initialBattleState: List<BattleGroup>

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
        initialBattleState = groups.toList()
        battleGroups = groups
    }

    fun battle(immuneSystemBooster: Int = 0): Pair<Int, BattleGroupType> {
        battleGroups = initialBattleState.map { it.copy() }.toMutableList()

        if (immuneSystemBooster > 0) {
            battleGroups.filter { it.type == BattleGroupType.IMMUNESYSTEM }.forEach { it.booster = immuneSystemBooster }
        }

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

        return battleGroups.sumOf { it.units } to battleGroups.first().type
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