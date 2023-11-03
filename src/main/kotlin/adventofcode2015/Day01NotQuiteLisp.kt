package adventofcode2015

class Day01NotQuiteLisp

class ParenthesisParser(val floorMap: String) {

    fun getFinalFloor(): Int {
        return floorMap.fold(0) { floor, value -> if (value == '(') floor + 1 else floor - 1 }
    }

    fun getFirstBasementStep(): Int {
        return floorMap.foldIndexed(0) { index, floor, value ->
            if (value == '(') {
                return@foldIndexed floor + 1
            } else {
                if (floor == 0) {
                    return index + 1
                }
                return@foldIndexed floor - 1
            }
        }
    }
}