import scala.collection.mutable.ArrayBuffer

case class Person(
  val firstName: String,
  val lastName: String = ""
)

val people = List(
  Person("Barney", "Rubble"),
  Person("Fred", "Flintstone")
)

val namesStartingWithB = for {
  p <- people // generator
  name = p.firstName // definition
  if name.startsWith("B") // filter
} yield name.toUpperCase

// the for comprehension depends on the implementation of the following four methods:
// 1. map
// 2. flatMap
// 3. withFilter
// 4. foreach

// to illustrate a bit further how for comprehensions work lets start with our own collection:

case class Sequence[A](initialElements: A*) {
  // this is purely for illustration and not meant for actual use.
  private val elements = scala.collection.mutable.ArrayBuffer[A]()

  elements ++= initialElements

  def foreach(block: A => Unit): Unit = {
    elements.foreach(block)
  }

  def map[B](f: A => B): Sequence[B] = {
    val newBuffer: ArrayBuffer[B] = elements.map(f) // remember that the array buffer is our dummy data structure
    Sequence(newBuffer: _*) // use vararg * syntax to create new sequqnce
  }

  def withFilter(p: A => Boolean): Sequence[A] = {
    val newBuffer = elements.filter(p)
    Sequence(newBuffer: _*)
  }

  // there is some serious cheating going on here, but they don't really relate to the example
  // at hand, so we'll just gloss over them
  def flatMap[B](f: A => Sequence[B]): Sequence[B] ={
    val mapRes: Sequence[Sequence[B]] = map(f)
    flattenLike(mapRes)
  }

  // same here, just pretend this is normal for arguments sake.
  def flattenLike[B](value: Sequence[Sequence[B]]): Sequence[B] = {
    var xs = ArrayBuffer[B]()
    for (listB: Sequence[B] <- value) {
      for (elem <- listB) {
        xs += elem
      }
    }
    Sequence(xs: _*)
  }
}

val myIntSequence = Sequence(1, 2, 3, 4)
val myStringSequnece = Sequence("A", "B", "C")

// this will only work once we implemented the foreach method in the class
for (i <- myIntSequence) println(i)

// next, trying to use it with yield requires the map method to be implemented
for {
  i <- myIntSequence
} yield i * 2


// next, lets try to use a filter.
for {
  i <- myIntSequence
  if i > 2
} yield i * 3

// we can use multiple generators when we want to do something on multiple collections:
// but in order to do that we need to have a flatMap method
val myFriends = Sequence(
  Person("Joe"),
  Person("Jack"),
  Person("Jim")
)

val petesFriends = Sequence(
  Person("Adam"),
  Person("Joe"),
  Person("Jim"),
  Person("Bernie")
)
val mutualFriends: Sequence[Person] = for {
  myFriend <- myFriends
  petesFriend <- petesFriends
  if (myFriend.firstName == petesFriend.firstName)
} yield myFriend

mutualFriends.foreach(println)