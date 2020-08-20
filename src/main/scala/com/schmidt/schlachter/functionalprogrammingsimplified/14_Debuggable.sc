// To bring the wrapper monad and the bind concept together, well come up with a new Wrapper type that not only
// allows us to return some result, but also debugging info, like in the bind example

case class Debuggable[A](value: A, log: List[String]) {
  def map[B](f: A => B): Debuggable[B] = {
    Debuggable(f(value), log)
  }
  def flatMap[B](f: A => Debuggable[B]): Debuggable[B]  = {
    val next = f(value)
    Debuggable(next.value, log ::: next.log)
  }
}

def f(a: Int): Debuggable[Int] = {
  val res = a * 3
  Debuggable(res, List(s" f($a) = $res"))
}
def g(a: Int): Debuggable[Int] = {
  val res = a * 2
  Debuggable(res, List(s" g($a) =  $res"))
}
def h(a: Int): Debuggable[Int] = {
  val res = a * 4
  Debuggable(res, List(s" h($a) =  $res"))
}

val intResult = for {
  a <- f(100)
  b <- g(a)
  c <- h(b)
} yield c


Debuggable[String]("A", List("My value is: "))