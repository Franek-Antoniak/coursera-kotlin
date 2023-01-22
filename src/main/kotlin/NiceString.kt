fun String.isClear(): Boolean {
    val set = hashSetOf("bu", "ba", "be")
    return this.zipWithNext().map { it.first.toString() + it.second }.none { set.contains(it) }
}

fun String.hasEnoughVowels(): Boolean {
    val set = hashSetOf('a', 'e', 'i', 'o', 'u')
    return this.count { set.contains(it) } >= 3
}

fun String.hasMirrorPair() = this.zipWithNext().any { it.first == it.second }


fun String.isNice() = listOf(isClear(), hasEnoughVowels(), hasMirrorPair()).count() { it } >= 2

fun main() {
    val res = listOf("bac" to false, "aza" to false, "abaca" to false, "baaa" to true, "aaab" to true)
    println(res.all { it.first.isNice() == it.second })
}