package ru.stillercode.logic

import kotlin.random.Random

class Tetris {
    private val board = Array(BOARD_SIZE.y) {IntArray(BOARD_SIZE.x)}
    private var activePiece = makeNewPiece()
    private var nextPiece = makeNewPiece()
    var isGameOver = false
    var score = 0
    init {
        activePiece.place()
    }
    private fun makeNewPiece(): Piece {
        val i = Random.nextInt(CLASSIC_SHAPES.size)
        return Piece(CLASSIC_SHAPES[i], i + 1)
    }

    fun getBoardArrayToDraw(): Array<IntArray> {
        val out = board.map { it.clone() }.toTypedArray()
        for (cell in activePiece.getActualShape()) {
            out[cell.y][cell.x] = activePiece.imageIndex
        }
        return out
    }
    fun getNextPieceArrayToDraw(): Array<IntArray> {
        val out = Array(BOARD_SIZE.y) {IntArray(PIECE_MAX_DIM.x)}
        for (cell in nextPiece.getShapeToDisplayAsNext()) {
            out[cell.y][cell.x] = nextPiece.imageIndex
        }
        return out
    }
    fun move(vec: XY) {
        activePiece.move(vec, board)
    }
    fun fall() {
        if (!activePiece.fall(board)) {
            activePiece = nextPiece
            activePiece.place()
            if (!activePiece.isPlacedValidly(board)) {
                isGameOver = true
            }
            nextPiece = makeNewPiece()
            removeLines()
        }
    }

    private fun removeLines() {
        for (y in BOARD_SIZE.y -1 downTo 0) {
            if (board[y].all{it > 0}) {
                removeLine(y)
                score++
                removeLines()
            }
        }
    }

    private fun removeLine(row: Int) {
        for (y in row downTo 1) {
            board[y] = board[y - 1]
        }
        board[0] = IntArray(BOARD_SIZE.x)
    }

    fun rotate() {
        activePiece.rotate(board)
    }
}