
// instead of declaring a function the normal way like so
def addNormal(a: Int, b: Int) = a + b

// we can also use multiple input parameter groups
def addWithParamGroups(a: Int)(b: Int)(c: Int) = a + b + c

// and call it like so
addWithParamGroups(10)(20)(30)

// these input groups can have multiple parameters
def addAndThenMultiply(a: Int, b: Int)(c: Int) = (a + b) * c
addAndThenMultiply(10, 10)(5)

// how is this useful? we can use it to define our own control structures.
// notice that _condition_ is a by-name parameter. This is crucial because we do not want the condition
// to be evaulated until it is called. Otherwise the compiler would optimize the call to whilst to
// whilst (0<5), and then further to whilst(true). Also note that we are seriously cheating here by using
// a while loop under the hood. Important right now is just the signature of whilst.
def whilst(condition: => Boolean)(codeBlock: => Unit): Unit = {
  while(condition) {
    codeBlock
  }
}

var i = 0
whilst(i < 5) {
  println(i)
  i += 1
}

def ifBothTrue(condition1: => Boolean)(condition2: => Boolean)(codeBlock: => Unit): Unit = {
  if (condition1 && condition2) {
    codeBlock
  }
}
ifBothTrue(10 < 20)('A'.isUpper) {
  println("Common sense gets you common results")
}

// using it with implicits is also possible
def printIntIfTrue(a: Int)(implicit b: Boolean): Unit = if (b) println(1)

// to call it we can either provide a boolean:
printIntIfTrue(1)(true)

// or declare an implicit boolean in this scope:
implicit val boo = true
printIntIfTrue(2)

// it will not work if there is only a 'regular' boolean in scope, it must be an implicit

// this technique can be useful in there is a shared resource you are accessing several times, like a DB connection.
// You don't need to pass it around all the time, it is just there. It works the same in the Actor model in Akka

// some rules:
// - you can only have one imlicit group: def asd(implicit a: Int)(implicit b: Int) will not compile
// - implicit must be the last parameter group: def asd(implicit a: Int)(b: Int) will not compile

// default values:
def defaultAdd(a: Int = 2)(b: Int = 3) = { a + b }
// to call this we must use one of the following
println(defaultAdd(1)(1))
println(defaultAdd()(10))
println(defaultAdd(10)())
println(defaultAdd()())