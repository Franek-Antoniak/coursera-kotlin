package ratio

import java.math.BigInteger


class Rational(var numerator: BigInteger, var denominator: BigInteger) : Comparable<Rational> {
    init {
        require(denominator != 0.toBigInteger()) { "Denominator must not be zero" }
        unify()
    }

    override fun toString(): String {
        unify()
        return if (denominator == 1.toBigInteger()) {
            "$numerator"
        } else {
            "$numerator/$denominator"
        }
    }

    private fun unify(): Rational {
        repairSign()
        reduceFraction()
        return this
    }

    private fun repairSign() {
        numerator = if (numerator.signum() * denominator.signum() < 0) -numerator.abs() else numerator.abs()
        denominator = denominator.abs()
    }

    private fun reduceFraction() {
        val gcd = numerator.gcd(denominator).abs()
        numerator /= gcd
        denominator /= gcd
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Rational) return false
        return numerator * other.denominator == denominator * other.numerator
    }

    override fun hashCode(): Int {
        return numerator.hashCode() * 31 + denominator.hashCode()
    }

    operator fun plus(other: Rational): Rational {
        val newNumerator = numerator * other.denominator + other.numerator * denominator
        val newDenominator = denominator * other.denominator
        return Rational(newNumerator, newDenominator).unify()
    }

    operator fun minus(other: Rational): Rational {
        val newNumerator = numerator * other.denominator - other.numerator * denominator
        val newDenominator = denominator * other.denominator
        return Rational(newNumerator, newDenominator).unify()
    }

    operator fun times(other: Rational): Rational {
        val newNumerator = numerator * other.numerator
        val newDenominator = denominator * other.denominator
        return Rational(newNumerator, newDenominator).unify()
    }

    operator fun unaryMinus(): Rational {
        return Rational(-numerator, denominator)
    }

    override operator fun compareTo(other: Rational): Int {
        return (numerator * other.denominator).compareTo(denominator * other.numerator)
    }

    operator fun div(other: Rational): Rational {
        val newNumerator = numerator * other.denominator
        val newDenominator = denominator * other.numerator
        return Rational(newNumerator, newDenominator).unify()
    }

    operator fun rangeTo(other: Rational) = RationalRange(this, other)

    class RationalRange(
        override val start: Rational,
        override val endInclusive: Rational
    ) : ClosedRange<Rational> {

        override fun equals(other: Any?): Boolean {
            return other is RationalRange && (isEmpty() && other.isEmpty() ||
                    start == other.start && endInclusive == other.endInclusive)
        }

        override fun hashCode(): Int {
            return if (isEmpty()) -1 else 31 * start.hashCode() + endInclusive.hashCode()
        }

        override fun toString(): String = "$start..$endInclusive"
    }
}

infix fun Int.divBy(i: Int): Rational {
    return Rational(this.toBigInteger(), i.toBigInteger())
}

fun String.toRational(): Rational {
    val (numerator, denominator) = split("/").let {
        if (it.size == 1) {
            it[0] to "1"
        } else {
            it[0] to it[1]
        }
    }
    return Rational(numerator.toBigInteger(), denominator.toBigInteger())
}

infix fun Long.divBy(l: Long): Rational {
    return Rational(this.toBigInteger(), l.toBigInteger())
}

infix fun BigInteger.divBy(other: BigInteger): Rational {
    return Rational(this, other)
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