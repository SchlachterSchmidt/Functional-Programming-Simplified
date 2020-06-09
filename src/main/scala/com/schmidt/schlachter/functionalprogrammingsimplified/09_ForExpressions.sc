case class Person(
  val firstName: String,
  val lastName: String
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
