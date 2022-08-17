package adventofcode2017

class Day01InverseCaptcha(val captcha: String) {

    private fun Char.toInteger(): Int = Integer.parseInt(this.toString())

    fun calculateCaptchaSum(): Int {
        val pairs = captcha.windowed(2, 1).map { it[0].toInteger() to it[1].toInteger() }.toMutableList()
        pairs.add(captcha.last().toInteger() to captcha.first().toInteger())
        return pairs.sumBy { (first, second) -> if (first == second) first else 0 }
    }

}