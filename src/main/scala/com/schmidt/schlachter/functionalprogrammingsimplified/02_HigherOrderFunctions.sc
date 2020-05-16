val list = List.range(0, 10)

def isEven(i: Int) = i % 2 == 0

// filter is an example of a higher order function (HOF) that takes another function as input parameter
val evens = list.filter(isEven)
println(evens)

// the scaladoc for filter reads something like
//    def filter(p: (A) => Boolean): List[A]
// meaning that filter takes as input parameter a predicate p that transforms the generic type A into a boolean

// in this sense, functional code is more higher level, you dont care how the filter works, you just express the
// condition that you are filtering for
def sayHello(callback: () => Unit): Unit = {
  callback()
}

def helloAl(): Unit = { println("Hello, Al") }
sayHello(helloAl)

// defining a function with only one input parameter we can omit the parenthesis:
// foo and bar are equivalent
//def foo(f: String => Int)
// def bar(f: (String) => Int) <== somehow the worksheet will not continue after this line

// we can mix function and regular input params
def executeNTimes(f: () => Unit, n: Int): Unit = {
  for (i <- 1 to n) f()
}

def helloWorld(): Unit = { println("Hello world") }
executeNTimes(helloWorld, 3)

// this example takes a function and two input values and applies the function to the values
def executeAndPrint(f: (Int, Int) => Int, x: Int, y: Int): Unit = {
  val result = f(x, y)
  println(result)
}
def sum(x: Int, y: Int) = x + y
def multiply(x: Int, y: Int) = x * y

executeAndPrint(sum, 5, 4)
executeAndPrint(multiply, 6, 6)

// and to build on that
def execTwoFunctions(f1: (Int, Int) => Int,
                     f2: (Int, Int) => Int,
                     x: Int,
                     y: Int): Tuple2[Int, Int] = {
  val result1 = f1(x, y)
  val result2 = f2(x, y)
  (result1, result2)
}

execTwoFunctions(sum, multiply, 5, 10)


// writing a map function
def mapMeSomeInts[A](f: Int => A, list: List[Int]): List[A] = {
  for {
    x <- list
  } yield f(x)
}

def double(i: Int): Int = i * 2
val resultDouble = mapMeSomeInts(double, list)
println(resultDouble)

// since there is nothing specific to Int inside the Map body, we can make it support any list
def mapMeSomeWhatEver[A, B](f: A => B, list: List[A]): List[B] = {
  for {
    x <- list
  } yield f(x)
}
val someStrings = List("John", "Joe", "Pete")

def length(a: String) = a.length
mapMeSomeWhatEver(length, someStrings)

def upper(a: String) = a.toUpperCase()
mapMeSomeWhatEver(upper, someStrings)

def triple(a: Int) = a * 3
mapMeSomeWhatEver(triple, list)

// there is also nothing in map that depends on List, so we can use the super type Seq
//    def mapMeSomeSequence[A, B](f: A => B, input: Seq[A]): Seq[B]

// exercise: implement a filter
def filterMeSomething[A](f: A => Boolean, input: Seq[A]): Seq[A] = {
  for {
    x <- input
    if f(x)
  } yield x
}

def startsWith(char: String): String => Boolean = {
  str => str.startsWith(char)
}
filterMeSomething(startsWith("P"), someStrings)
