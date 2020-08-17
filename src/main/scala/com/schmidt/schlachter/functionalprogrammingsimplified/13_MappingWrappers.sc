// Not only can we map, flatMap, and use for comprehension for collections, but also for wrappers like Option and Try.
// we'll write a Wrapper class that
// - takes a single Int param
// - implements map and flatMap so we can use it in a for-expression with multiple generators
// - the map and flatMap methods return the same Wrapper[Int] retult type

class Wrapper[A] private (value: A) {
  def map[B](f: A => B): Wrapper[B] = {
    val res = f(value)
    new Wrapper(res)
  }

  def flatMap[B](f: A => Wrapper[B]): Wrapper[B] =
    f(value)

  override def toString: String = value.toString
}

// rather than making a case class, we'll use a companion object this time
// since the apply methods 'lifts' the value into our wrapper monad
object Wrapper {
  def apply[A](value: A): Wrapper[A] = {
    new Wrapper[A](value)
  }
}

val intResult: Wrapper[Int] = for {
  a <-  Wrapper(1)
  b <- Wrapper(2)
  c <-  Wrapper(3)
} yield a + b + c

println(intResult)

val stringResult: Wrapper[String] = for {
  a <- Wrapper("a")
  b <- Wrapper("b")
  c <- Wrapper("c")
  d <- Wrapper("d")
} yield a + b + c + d

