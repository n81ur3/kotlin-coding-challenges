package adventofcode2017

class Day25TheHaltingProblem

data class StateResult(val writeValue: Int, val nextDirection: Int, val nextState: TuringState)
data class StateRule(val ruleOnNull: StateResult, val ruleOnOne: StateResult)

sealed class TuringState {
    object A : TuringState()
    object B : TuringState()
    object C : TuringState()
    object D : TuringState()
    object E : TuringState()
    object F : TuringState()

    companion object {
        fun fromChar(state: Char): TuringState = when (state) {
            'A' -> A
            'B' -> B
            'C' -> C
            'D' -> D
            'E' -> E
            else -> F
        }
    }
}

class TuringMachine(stateRules: List<String>) {
    val states = mutableMapOf<TuringState, StateRule>()
    var currentState: TuringState = TuringState.A
    var currentPosition = 0
    val tape = mutableMapOf(0 to 0)
    val checksum: Int
        get() = tape.values.sum()

    init {
        stateRules.windowed(9, 10) { lines ->
            states[TuringState.fromChar(lines[0][9])] = parseRule(lines.subList(1, 9))
        }
    }

    private fun parseRule(input: List<String>): StateRule {
        /* Sample
        In state A:
          If the current value is 0:
            - Write the value 1.
            - Move one slot to the right.
            - Continue with state B.
          If the current value is 1:
            - Write the value 0.
            - Move one slot to the left.
            - Continue with state D.
         */
        val writeValueOnNull = input[1][22].code - 48
        val nextDirectionOnNull = when (input[2][27]) {
            'r' -> 1
            else -> -1
        }
        val nextStateOnNull = TuringState.fromChar(input[3][26])

        val writeValueOnOne = input[5][22].code - 48

        val nextDirectionOnOne = when (input[6][27]) {
            'r' -> 1
            else -> -1
        }

        val nextStateOnOne = TuringState.fromChar(input[7][26])

        val resultOnNull = StateResult(writeValueOnNull, nextDirectionOnNull, nextStateOnNull)
        val resultOnOne = StateResult(writeValueOnOne, nextDirectionOnOne, nextStateOnOne)

        return StateRule(resultOnNull, resultOnOne)
    }

    fun run(numberOfSteps: Int) {
        repeat(numberOfSteps) { step() }
    }

    fun step() {
        val tapeValue = tape[currentPosition] ?: 0
        val currentStateRule = states.get(currentState)
        require(currentStateRule != null)
        val nextStateRule = if (tapeValue == 0) currentStateRule.ruleOnNull else currentStateRule.ruleOnOne
        with(nextStateRule) {
            tape[currentPosition] = writeValue
            currentPosition += nextDirection
            currentState = nextState
        }
    }
}