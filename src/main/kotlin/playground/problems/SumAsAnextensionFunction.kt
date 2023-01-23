package playground.problems

fun List<Int>.sum() = fold(0) { sum, element -> sum + element }

fun main(args: Array<String>) {
    val sum = listOf(1, 2, 3).sum()
    println(sum)    // 6
}