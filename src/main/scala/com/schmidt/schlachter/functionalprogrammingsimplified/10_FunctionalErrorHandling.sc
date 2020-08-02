// a pure function cannot throw an exception. It would violate the contract that the function definition implies:

// bad. Can throw an exception without even telling you. The contract is that it _always_ returns an int, not some of the time
def someStringToInt(a: String): Int = a.toInt

// the functional way to handle this is via an Option
def makeInt(s: String): Option[Int] = {
  try {
    Some(s.trim.toInt)
  } catch {
    case e: Exception => None
  }
}

// and we can use it like so
makeInt("123") match {
  case Some(i) => println(s"i = $i")
  case None => println("could not parse input")
}

// there are in addition to Options Try and Either as well as 3rd party approaches

// this is a slightly more advanced example, the happy case:
val result = for {
  x <- makeInt("1")
  y <- makeInt("2")
  z <- makeInt("3")
} yield x * y + z
println(result)

// but if we cannot parse one of the numbers:
val noResult = for {
  x <- makeInt("1")
  y <- makeInt("I cannot be parsed")
  z <- makeInt("3")
} yield x * y + z
println(noResult)
// makeInt returns an Option and Option, Some and None implement map and flatMap, the result
// ends up being bound to None

// we can then further deal with the result as before:
result match {
  case Some(i) => println(s"x * y + z = $i")
  case None => println("some smartass didn't provide a number")
}

// In FP, we use Options instead of exceptions or Null values.
// If we need to have more information about the reason of the failure, we can use a Try / Success / Failure

import scala.util.{Failure, Success, Try}

def tryMakeInt(s: String): Try[Int] = {
  Try(s.trim.toInt)
}
println(tryMakeInt("1"))
tryMakeInt("foo") match {
  case Success(i) => println(s"Success, value is $i")
  case Failure(s) => println(s"Failure, reason $s")
}

val answer = for {
  a <- tryMakeInt("3")
  b <- tryMakeInt("4")
} yield a * b

// There is also Either / Left / Right. While Try has explicit names for Success and Failure, Either
// relies on convention:
// - Left holds the error
// - Right holds the success value

import scala.util.{Either, Left, Right}
def eitherMakeInt(s: String): Either[String, Int] = {
  try {
    Right(s.trim.toInt)
  } catch {
    case e: Exception => Left(e.toString)
  }
}
eitherMakeInt("123")
eitherMakeInt("bar") match {
  case Left(s) => println(s"Error: $s")
  case Right(i) => println(s"Success, $i")
}
// Since Scala 2.12, Either also supplies map and flatMap so it can be used in for expressions

// the Null object pattern is used by eg. the filter method to return an empty result rather
// than an option when appropriate:
def doSomething(x: List[Int]): List[Int] = {
  if (x.size > 2) {
    x.slice(0, 1)
  } else {
    Nil: List[Int]
  }
}
doSomething(List(1, 2, 3, 4, 5))

// the same pattern also applies to Null values. Pretending that we have
// a response from some HTTP client that might be null, we want to wrap it in
// an Option / Either / Try to remove ambiguity

// String standing in for a proper class here that we may have received from our HTTP client
case class Response(body: String, status: Int)

def handleResponse(r: Response): Option[String] = {
  r match {
    case rs if rs.status > 400 => None
    case rs if rs.body == null => None
    case rs if rs.status == 200 => Some(r.body)
  }
}

val responseError = Response(null, 500)
handleResponse(responseError)

val responseSuccess = Response("message", 200)
handleResponse(responseSuccess)


// Addendum: Or is a 3rd party approach to Either / Try
import org.scalactic.{ErrorMessage, Or, Good, Bad}
// what is implied with Option or Either is very clear with Or
def makeIntOr(s: String): Int Or ErrorMessage = {
  try {
    Good(s.trim.toInt)
  } catch {
    case e: Exception => Bad(e.toString)
  }
}
println(makeIntOr("123"))
makeIntOr("baz") match {
  case Good(i) => println(s"good $i")
  case Bad(b) => println(s"bad: $b")
}
