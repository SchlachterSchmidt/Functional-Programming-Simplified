/* when you see curly braces a lot of different things can could be happening. Ex:

val x = FOO {
 // some code
}
could be
- an anonymous class
- a function that takes a by-name parameter

and

val x = FOO { (s: State) =>
  // some more code
}

could be
- a class that takes a function parameter
- a function that takes a by-name parameter

Examples of each below
*/

// 1. An Anonymous class

// assuming the existence of
trait Person {
  def name: String
  def age: Int
  override def toString = s"name: $name, age: $age"
}

// Person cannot be a case class! (they dont allow the use of 'new', and they must have at least one constructor param
// Person could be a regular class, but if name and age are defined within the class, they would require an override modifyer
val mary = new Person {
  val name = "Mary"
  val age = 22
}

// 2. A function taking a by-name parameter

// assuming the existence of
def timer[A](block: => A) = {
  val start = System.nanoTime
  val result = block
  val finish = System.nanoTime
  val duration = finish - start
  (result, duration/1000000d)
}

val (result, time) = timer {
  Thread.sleep(1000)
  "I ran inside the timer"
}

// 3. A class that takes function parameter

// assuming
case class StringToInt(run: String => Int)

// creating an instance of this case class which takes a function as input
val stringToIntByLength = StringToInt {s: String =>
  println("I am passed as a function")
  s.length
}
stringToIntByLength.run("Banana")

// and a slightly more complex example of this
case class Transform[A, B](run: (A, A) => B)

val transformTwoStringsToInt = Transform { (a: String, b: String) =>
  println("I am a more complex example")
  a.length + b.length
}
transformTwoStringsToInt.run("foo", "bar")

// 4 A function that takes another function as input parameter

// given this function with two parameter groups we can define the input
// and the behaviour separately
def stringToIntBy(s: String)(f: String => Int) = f(s)

stringToIntBy("Hello there") { s: String =>
  println("I am another example")
  s.length
}

// it could also be implemented as case class like so
case class StringToIntBy(s: String)(_fun: String => Int) {
  def fun = _fun
}
StringToIntBy("Hello") {s: String =>
  println("How many ways are there to do the same thing?")
  s.length
}

































