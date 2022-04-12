package google.bootcamp.example.myapp.annotations

import kotlin.reflect.full.*

annotation class ImPlant

@Target(AnnotationTarget.PROPERTY_GETTER)
annotation class OnGet

@Target(AnnotationTarget.PROPERTY_SETTER)
annotation class OnSet

@ImPlant
class Plant {
    @get:OnGet
    val isGrowing = true

    @set:OnSet
    var needsFood = false

    fun trim() {}
    fun fertilize() {}
}

fun testAnnotations() {
    val classObj = Plant::class
    val myAnnotationObject = classObj.findAnnotation<ImPlant>()
    println(myAnnotationObject)
}

fun labels() {
    loop@ for (i in 1..100) {
        println("outer: $i")
        if ((i % 2) == 1) continue
        for (j in 1..100) {
            if (i > 10) continue@loop
            print("[$i/$j]")
        }
        println()
    }
}

fun getResult() = "result"

fun String.receiver(block: String.() -> String) {
    println(block(this))
}

fun main() {
    "helmut bierbaumer".receiver { getResult() }
}