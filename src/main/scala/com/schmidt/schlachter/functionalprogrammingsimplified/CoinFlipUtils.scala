package com.schmidt.schlachter.functionalprogrammingsimplified

import scala.util.Random

object CoinFlipUtils {

  def showPrompt(): Unit = { println("\n(h)eads, (t)ails, (q)uit: ") }

  def getUserInput = readLine.trim.toUpperCase

  def printableFlipResult(flip: String) = flip match {
    case "H" => "Heads"
    case "T" => "Tails"
  }

  def printGameState(state: GameState): Unit = {
    println(s"#Flips: ${state.numFlips}, #Correct: ${state.numCorrectGuesses}")
  }

  def printGameState(printableResult: String, gameState: GameState): Unit = {
    println(s"Flip was $printableResult")
    printGameState(gameState)
  }

  def printGameOver: Unit = println("\n===GAME OVER===")

  def tossCoin(r: Random) = {
    val i = r.nextInt(2)
    i match {
      case 0 => "H"
      case 1 => "T"
    }
  }

}
