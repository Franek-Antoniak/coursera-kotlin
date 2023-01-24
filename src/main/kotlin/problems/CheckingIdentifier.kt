package problems

fun isValidIdentifier(s: String): Boolean = s.matches(Regex("""[a-zA-Z_]\w*"""))

fun main() {
    println(problems.isValidIdentifier("name"))   // true
    println(problems.isValidIdentifier("_name"))  // true
    println(problems.isValidIdentifier("_12"))    // true
    println(problems.isValidIdentifier(""))       // false
    println(problems.isValidIdentifier("012"))    // false
    println(problems.isValidIdentifier("no$"))    // false
}