fun main(args: Array<String>) {
    val s = Object()
    println(s as? Int)    // null
    println(s as Int?)    // exception
}