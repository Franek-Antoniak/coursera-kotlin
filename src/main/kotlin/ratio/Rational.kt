package ratio

import ratio.Rational.Companion.create
import java.math.BigInteger

@Suppress("DataClassPrivateConstructor")
data class Rational
private constructor(val numerator: BigInteger, val denominator: BigInteger) : Comparable<Rational> {
    companion object {
        fun create(numerator: BigInteger, denominator: BigInteger) = unify(numerator, denominator)
        private fun unify(numerator: BigInteger, denominator: BigInteger): Rational {
            require(denominator != 0.toBigInteger()) { "Denominator must not be zero" }
            val n = if (numerator.signum() * denominator.signum() < 0) -numerator.abs() else numerator
                .abs()
            val d = denominator.abs()
            val gcd = numerator.gcd(denominator).abs()
            return Rational(n / gcd, d / gcd)
        }
    }

    override fun toString(): String {
        return if (denominator == 1.toBigInteger()) "$numerator" else "$numerator/$denominator"
    }

    operator fun plus(other: Rational): Rational {
        val newNumerator = numerator * other.denominator + other.numerator * denominator
        val newDenominator = denominator * other.denominator
        return create(newNumerator, newDenominator)
    }

    operator fun minus(other: Rational): Rational {
        val newNumerator = numerator * other.denominator - other.numerator * denominator
        val newDenominator = denominator * other.denominator
        return create(newNumerator, newDenominator)
    }

    operator fun times(other: Rational): Rational {
        val newNumerator = numerator * other.numerator
        val newDenominator = denominator * other.denominator
        return create(newNumerator, newDenominator)
    }

    operator fun unaryMinus(): Rational {
        return create(-numerator, denominator)
    }

    override operator fun compareTo(other: Rational): Int {
        return (numerator * other.denominator).compareTo(denominator * other.numerator)
    }

    operator fun div(other: Rational): Rational {
        val newNumerator = numerator * other.denominator
        val newDenominator = denominator * other.numerator
        return create(newNumerator, newDenominator)
    }
}


infix fun Int.divBy(i: Int): Rational {
    return create(this.toBigInteger(), i.toBigInteger())
}

fun String.toRational(): Rational {
    fun String.toBigIntegerOrFail(): BigInteger = this.toBigIntegerOrNull()
        ?: throw IllegalArgumentException(
            "String must be in format 'numerator/denominator' or 'numerator', was: " +
                    this@toRational
        )
    if (!contains("/")) {
        return create(toBigIntegerOrFail(), 1.toBigInteger())
    }
    val (n, d) = split("/")
    return create(n.toBigIntegerOrFail(), d.toBigIntegerOrFail())
}

infix fun Long.divBy(l: Long): Rational {
    return create(this.toBigInteger(), l.toBigInteger())
}

infix fun BigInteger.divBy(other: BigInteger): Rational {
    return create(this, other)
}


fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    1.toBigDecimal()..2.toBigDecimal()

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println(
        "912016490186296920119201192141970416029".toBigInteger() divBy
                "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2
    )
}