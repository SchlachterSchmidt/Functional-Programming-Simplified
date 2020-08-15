// given two functions that both take ints we can glue them easily

def f(a: Int): Int = a * 3
def g(a: Int): Int = a * 2
var r = g(f(100))

// however, if they return not only an int things get more messy
def fs(a: Int): (String, Int) = {
  val res = a * 3
  (s"the result of fs is $res", res)
}
def gs(a: Int): (String, Int) = {
  val res = a * 3
  (s"the result of gs is $res", res)
}
val (fString, fInt) = fs(100)
val (gString, gInt) = gs(fInt)
val msg = fString + " " + gString

// we can write thsi bind function that can do the job for us
def bind(fun: Int => (String, Int), tup: (String, Int)): (String, Int) = {
  val (stringRes, intRes) = fun(tup._2)
  (tup._1 + stringRes, intRes)
}

val fRes = fs(100)
val gRes = bind(gs, fRes)