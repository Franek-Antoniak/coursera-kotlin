package games.gameOfFifteen

import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game
import games.game2048.Game2048Initializer
import games.game2048.addNewValue
import games.game2048.moveValues
import games.game2048.moveValuesInRowOrColumn

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
        GameOfFifteen(initializer)

class GameOfFifteen(private val initializer: GameOfFifteenInitializer) : Game {
    private val board = createGameBoard<Int?>(4)
    override fun initialize() {
        board.getAllCells().zip(initializer.initialPermutation).forEach { (cell, value) ->
            board[cell] = value
        }
    }

    override fun canMove(): Boolean {
        return board.any { it == null }
    }

    override fun hasWon(): Boolean {
        return board.getAllCells().zip(1..15).all { (cell, value) ->
            board[cell] == value
        }
    }

    override fun processMove(direction: Direction) {
        val emptyCell = board.find { it == null }!!
        val cellToMove = board.run { emptyCell.getNeighbour(direction.reversed()) }
        if (cellToMove != null) {
            board[emptyCell] = board[cellToMove]
            board[cellToMove] = null
        }
    }

    override fun get(i: Int, j: Int): Int? {
        return board.run { get(getCell(i, j)) }
    }
}