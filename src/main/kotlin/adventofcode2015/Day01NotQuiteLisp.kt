package adventofcode2015

class Day01NotQuiteLisp

class ParenthesisParser(val floorMap: String) {

    fun getFinalFloor(): Int {
        return floorMap.fold(0) { floor, value -> if (value == '(') floor + 1 else floor - 1}
    }
}