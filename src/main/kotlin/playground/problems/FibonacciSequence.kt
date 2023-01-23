package playground.problems

fun fibonacci(): Sequence<Int> = sequence {
    var stack = 0 to 1
    while (true) {
        yield(stack.first)
        stack = stack.second to stack.first + stack.second
    }
}

fun main(args: Array<String>) {
    fibonacci().take(4).toList().toString() eq
            "[0, 1, 1, 2]"

    fibonacci().take(10).toList().toString() eq
            "[0, 1, 1, 2, 3, 5, 8, 13, 21, 34]"
}