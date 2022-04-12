package google.bootcamp.example.myapp

data class Fish(var name: String)

fun fishExamples() {
    val fish = Fish("splashy")
    myWith(fish.name) {
        println(capitalize())
    }

    val fish2 = Fish(name = "splashy").apply { name = "sharky" }
    println(fish2.name)

    println(fish.let { it.name.capitalize() }
        .let { it + "fish" }
        .let { it.length }
        .let { it + 31 })
}

inline fun myWith(name: String, block: String.() -> Unit) {
    name.block()
}

fun main() {
    fishExamples()
}