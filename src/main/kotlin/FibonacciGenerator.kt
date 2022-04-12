class FibonacciGenerator {

    fun fib(n: Int): Int {
        if (n < 2) return n
        else return fib(n - 2) + fib(n - 1)
    }
}