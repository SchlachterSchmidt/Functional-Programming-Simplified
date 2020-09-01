package com.schmidt.schlachter.functionalprogrammingsimplified

// IO is always impure, but we can wrap it in a monad to be clear about the fact that we are impure.

object test extends App {
  def getLine: IO[String] = IO(scala.io.StdIn.readLine())
  def print(s: String): IO[Unit] = IO(println(s))

  for {
    _ <- print("First Name")
    firstName <- getLine
    _ <- print("Last Name")
    lastName <- getLine
    _ <- print(s"$firstName $lastName")
  } yield () // () is shorthand to return Unit.
}
