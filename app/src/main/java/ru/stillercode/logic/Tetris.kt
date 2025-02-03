package ru.stillercode.logic

class Tetris {
    val board = Array(BOARD_SIZE.y) {IntArray(BOARD_SIZE.x)}
    var activePiece = makeNewPiece()
    var nextPiece = makeNewPiece()
    var score = 0
    init {

    }
    private fun makeNewPiece(): Piece {
        //TODO

        return Piece(CLASSIC_SHAPES[0], 1)
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
        for (cell in activePiece.getShapeToDisplayAsNext()) {
            out[cell.y][cell.x] = activePiece.imageIndex
        }
        return out
    }
    fun move(vec: XY) {
        activePiece.move(vec, board)
    }
    fun fall() {
        if (!activePiece.fall(board)) {
            activePiece = nextPiece
            nextPiece = makeNewPiece()
        }
    }

    fun rotate() {
        activePiece.rotate(board)
    }
}