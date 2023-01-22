package npe

open class A(open val value: String) {
    init {
        println(value.length)
    }
}

class B(override val value: String) : A(value)

fun main(args: Array<String>) {
    val a = B("npe")

}