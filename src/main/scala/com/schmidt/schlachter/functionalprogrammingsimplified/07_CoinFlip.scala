package com.schmidt.schlachter.functionalprogrammingsimplified

import scala.util.Random
import CoinFlipUtils.getUserInput
import CoinFlipUtils.showPrompt
import CoinFlipUtils.tossCoin
import CoinFlipUtils.printableFlipResult
import CoinFlipUtils.printGameOver
import CoinFlipUtils.printGameState

import scala.annotation.tailrec

case class GameState(
  numFlips: Int,
  numCorrectGuesses: Int
)

object CoinFlip extends App {
  val state = GameState(0, 0)
  val random = new Random()

  mainLoop(state, random)

  @tailrec
  def mainLoop(state: GameState, random: Random): Unit = {

    showPrompt()
    val userInput = getUserInput

    userInput match {
      case "H" | "T" =>
        val coinTossResult = tossCoin(random)
        val newNumFlips = state.numFlips + 1
        if (userInput == coinTossResult) {
          val newNumCorrect = state.numCorrectGuesses + 1
          val newState = state.copy(newNumFlips, newNumCorrect)
          printGameState(
            printableFlipResult(coinTossResult),
            newState
          )
          mainLoop(newState, random)
        }
        else {
          val newState = state.copy(numFlips = newNumFlips)
          printGameState(
            printableFlipResult(coinTossResult),
            newState
          )
          mainLoop(newState, random)
        }
      case _ =>
        printGameState(state)
        printGameOver
    }
  }
}