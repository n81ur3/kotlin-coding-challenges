package adventofcode2017


class Day07RecursiveCircus

data class RCProgram(val name: String, val weight: Int, val dependants: List<String>)
data class WeightedChild(
    val name: String,
    val parent: String,
    val weight: Int,
    val dependants: List<String>,
    val dependantsWeight: Int
)

class RCTower(val programms: List<RCProgram>) {
    val childWeights = mutableListOf<WeightedChild>()
    var childsInequality = 0

    companion object {
        fun fromLines(input: List<String>): RCTower {
            return RCTower(input.map { parseInputLine(it) })
        }

        private fun parseInputLine(line: String): RCProgram {
            // sample: fbmhsy (58) -> lqggzbu, xwete, vdarulb, rkobinu, ztoyd, vozjzra
            val name = line.substringBefore(" ")
            val weight = line.substringAfter("(").substringBefore(")").toInt()
            val dependants = when {
                line.contains("->") -> line.substringAfter("-> ").split(", ")
                else -> emptyList()
            }
            return RCProgram(name, weight, dependants)
        }
    }

    fun getDependantsCount(programname: String): Int {
        val program = programms.find { it.name == programname }

        program?.let {
            if (program.dependants.isEmpty()) {
                return 1
            } else {
                return program.dependants.sumOf { getDependantsCount(it) }
            }
        }
        return 0
    }

    fun getDependantsWeightSum(programname: String) = getDependantsWeights(programname).sum()

    fun getDependantsWeights(programname: String): List<Int> {
        val program = programms.find { it.name == programname }

        program?.dependants?.let {
            if (program.dependants.isEmpty()) {
                return listOf(program.weight)
            } else {
                val childrenWeights = program.dependants.flatMap { getDependantsWeights(it) }
                return listOf(program.weight) + childrenWeights
            }
        }
        return listOf()
    }

    fun checkChildsForEqualWeight(programname: String): Int {
        buildWeightedChildList()
        val program = programms.find { it.name == programname }

        program?.dependants?.let {
            if (program.dependants.isNotEmpty()) {
                program.dependants.forEach { checkChildsForEquality(it) }
            }
            return childsInequality
        }
        return childsInequality
    }

    fun checkChildsForEquality(programname: String): Int {
        val program = programms.find { it.name == programname }

        program?.dependants?.let {
            if (program.dependants.isNotEmpty()) {
                val children =
                    program.dependants.map { childWeights.find { child -> child.name == it } }
                        .sortedBy { it?.dependantsWeight }
                val distinct = children.last()
                val others = children.first()
                distinct?.let {
                    others?.let {
                        val difference = Math.abs(distinct.dependantsWeight - others.dependantsWeight)
                        if (difference != 0) {
                            childsInequality = distinct.weight - difference
                        }
                    }
                }
            }
            program.dependants.forEach { checkChildsForEquality(it) }
        }

        return childsInequality
    }

    fun findRoot(): String = programms.maxByOrNull { program -> getDependantsCount(program.name) }?.name ?: ""

    private fun buildWeightedChildList() {
        val root = findRoot()
        addToWeightedChildList(root)
    }

    private fun addToWeightedChildList(programname: String) {
        val program = programms.find { it.name == programname }

        program?.dependants?.let {
            if (program.dependants.isEmpty()) return

            for (childname in program.dependants) {
                val child = programms.find { it.name == childname }
                childWeights.add(
                    WeightedChild(
                        child!!.name,
                        programname,
                        child.weight,
                        child.dependants,
                        getDependantsWeightSum(child.name)
                    )
                )
                addToWeightedChildList(childname)
            }
        }
    }
}