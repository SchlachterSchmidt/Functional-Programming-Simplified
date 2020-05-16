// In Scala, things are passed by value (think pointer to an object).
// Passing by name refers to the concept of passing an arbitrary block of code to be executed
// for example, if we want to write a timer function, that executes a piece of code and returns the result, along with
// the time it took to execute, we can try generics, but quickly break down since there can be an arbitrary number of arguments
// We can instead define a function that accepts an argument by name like so

def timer[A](blockOfCode: => A) = {
  val startTime = System.nanoTime
  val result = blockOfCode
  val stopTime = System.nanoTime
  val delta = stopTime - startTime
  (result, delta/1000000d)
}

// the timer method uses the by-name syntax, and because we are returning the generic type A, we can receive back
// Unit, Int, Map, Seq etc and we can use it for all sorts of things
val (result1, time1 ) = timer(println("Hello world"))
println(time1)

val (result2, time2) = timer { (1 to 100).toList.map { it => it*2 } }
println(result2)
println(time2)

// when passing by name, the code is not executed until it is called
def test[A](blockOfCode: => A) = {
  println("before the first execution")
  val a = blockOfCode
  println(a)
  Thread.sleep(10)

  println("before the second execution")
  val b = blockOfCode
  println(b)
  Thread.sleep(20)

  println("before the third execution")
  val c = blockOfCode
  println(c)
}
test { System.currentTimeMillis }
// the output shows that the code block is evaluated each time anew

// to show how this can be much nicer than using a functional input parameter as before:
def myAssert(f: () => Boolean): Unit =
  if (!f()) throw new AssertionError


myAssert(() => 5 > 3) // works, but it is ugly

def myOtherAssert(f: => Boolean) =
  if(!f) throw new AssertionError

myOtherAssert(5 > 3) // much cleaner
