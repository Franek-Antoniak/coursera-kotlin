interface X {
    var first: Int
    var second: Int
    var third: Int
}

interface Y {
    fun start()
    fun finish()
}

interface Z {
    fun init()
}

fun foo(x: X, y: Y?, z: Z) {
    x.run {
        first = 1
        second = 2
        third = 3
    }
    y?.run {
        start()
        finish()
    }
    val result = z.apply {
        init()
    }
}