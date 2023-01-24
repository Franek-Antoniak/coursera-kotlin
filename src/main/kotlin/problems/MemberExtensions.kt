package problems

class Words {
    private val list = mutableListOf<String>()

    fun String.record() {
        list += this
    }

    operator fun String.unaryPlus() {
        list += this
    }

    override fun toString() = list.toString()
}

fun main(args: Array<String>) {
    val words = Words()
    with(words) {
        // The following two lines should compile:
        "one".record()
        + "two"
    }
    words.toString() eq "[one, two]"
}