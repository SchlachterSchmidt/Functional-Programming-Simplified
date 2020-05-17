// tl;dr of currying: Functions that take multiple input parameters can be translated into multiple functions each taking only one input parameter:
// result = f(x)(y)(z)
// is mathematically equivalent to
// f1 = f(x)
// f2 = f1(y)
// result = f2(z)

// tl;dr of partially applied functions:
// function application just mean to apply a given function to it's input
// partial application means to apply a function to some of it's inputs, and return a function with fewer inputs

// Partial application example:
def plus(a: Int)(b: Int) = a + b
def plus2:Int => Int = plus(2)(_)
// plus2 is a function that takes an int, and returns and int
// you can also think of it as plus(a: Int) = a + 2
plus2(2)
plus2(3)

// the benefit of this technique is that it allows you to create specialized functins from
// generalized ones.

// for example this function can be be used to wrap any string
def wrap(prefix: String)(body: String)(suffix: String) = {
  prefix + body + suffix
}
val hello = "Hello, World"
val result = wrap("<div>")(hello)("</div")

// we can easily create a nicer interface for wrapping strings with divs:
def wrapWithDivs = wrap("<div>")(_: String)("</div>")
wrapWithDivs(hello)

// a more verbose way of writing basically the same thing (makes the input argument type clearer to me)
def wrapWithDivExpanded: String => String = {
  string => wrap("dev")(string)("dev")
}

def wrapWithP = wrap("<p>")(_: String)("</p>")

wrapWithDivs(wrapWithP(hello))

// creating curried functions from regular functions

// this is a regular function with one parameter group
def add(x: Int, y: Int) = x + y
// declaring a function2 instance from it like so
val addFunction = add _

// and creating a curried function from it like so
val addCurried = addFunction.curried
addCurried(1)(3)

// from here we can easily create partial functions:
val addCurried2 = addCurried(2)
addCurried2(3)
