var counter = 0
val foo: Int
    get() = counter++

fun main() {
    println(foo)
    println(foo)
    println(foo)
}