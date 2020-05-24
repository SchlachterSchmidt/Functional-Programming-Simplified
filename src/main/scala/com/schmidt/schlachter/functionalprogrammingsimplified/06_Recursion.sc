import scala.annotation.tailrec

val list = (1 to 10).toList

def sum(list: List[Int]): Int = list match {
  case Nil => {
    println("nil reached")
    0
  }
  case head :: tail => {
    println(s"head=$head - tail=$tail")
    head + sum(tail)
  }
}
sum(list)

@tailrec
def sumWithAccumulator(list: List[Int], accumulator: Int): Int = list match {
  case Nil => accumulator
  case x :: xs => sumWithAccumulator(xs, accumulator + x)
}

// making it tail recursive
def tailRecSum(list: List[Int]): Int = sumWithAccumulator(list, 0)

tailRecSum(list)