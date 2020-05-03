// this file contains some examples from the beginning of the book to about chapter 20

case class Person(firstName: String, lastName: String) {
  def someClassMethod(): Unit = {
    println("I am a class method. I am called for my side effects and am not pure. " +
      "Also, look at my signature and you'll notice that it says nothing about what I do." +
      "I am only called for my side effects.")
  }
}

// some basic syntax
sealed trait Shape
case class Circle(radius: Double) extends Shape
case class Square(length: Double) extends Shape
case class Rectangle(length: Double, width: Double) extends Shape

// Copy instead of modify to maintain immutability
val p1 = Person("Joe", "Swanson")
val p2 = p1.copy("Joseph")

val someImmutableValue: String = "Hello"
def somePureFunction(input: String): String = {
  input.toUpperCase()
}

// functional, expression oriented programming in a nutshell:
val result = somePureFunction(someImmutableValue)

// contrast this with OOP style:
p1.someClassMethod()

// `someClassMethod is a statement, `somePureFunction` is an expression.
// Expressions return things and have no side effects. Some more examples
val doubles = List(1, 2, 3, 4, 5).map(_ * 2)
val one = 0 + 1 // plus is an expression
val two = 2 * 1 // times is an expression
val greater = if (one > two) one else two // `if / else` can be an expression

val evenOrOdd = greater match {
  case 1 | 3 | 5 | 7 | 9 => "odd"
  case 2 | 4 | 6 | 8 | 10 => "even"
}

// and even try / catch:
def toInt(s: String): Int = {
  try {
    s.toInt
  } catch {
    case _: Throwable => 0
  }
}


// functions are variables too
val name = "A1"
val weight = 85
val double = (i: Int) => i * 2

// a function literal can be assigned to a variable like in the example above. But more
// formally: in a function like `xs.map(x => x * x)`, the `x => x * 2` part
// is the function literal. So we can
val aFun = (x: Int) => x * 2
val aResult = doubles.map(aFun)
println(aResult)

// Scala supports implicit return types (like boolean in this case
val isEven = (i: Int) => i % 2 == 0
val isEvenResult = doubles.map(isEven)
println(isEvenResult)

val sum = (a: Int, b: Int) => a + b
val sumResult = doubles.reduce(sum)
println(sumResult)

// behind the scenes, the compiler translates the above to this code, where Function2 is a
// _trait_, a functional interface accepting two input arguments
val sumVerbose = new Function2[Int, Int, Int] {
  def apply(a: Int, b: Int): Int = a + b
}

val sumVerboseResult = doubles.reduce(sumVerbose)
println(sumVerboseResult)

val resultOfSomeMethodChain = (1 to 100).toList
  .filter(isEven)
  .map(double)
  .reduce(sum)
println(resultOfSomeMethodChain)

// How to write functions that accept some function as argument
def myVeryOwnFilter(p: (Int) => Boolean): List[Int]

// it is possible to add function vals to a collection:
val functions = Map(
  "2x" -> double,
  "isEven" -> isEven
)
// and retrieve them from the Map:
val dub = functions("2x")
println(dub(55))

val fullName = (p: Person) => s"${p.firstName} ${p.lastName}"
println(fullName(p2))
