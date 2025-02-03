package ru.stillercode.logic

class Piece(shapes: List<Set<XY>>, val imageIndex: Int) {
    private val shapeSwitcher = Switcher(shapes)
    private var position = START_POINT

    fun getShapeToDisplayAsNext(): Set<XY> {
        val temp = position
        position = XY(0, 0)
        val out = getActualShape()
        position = temp
        return out
    }

    fun getActualShape(): Set<XY> {
        return getShapeAfterMove(shapeSwitcher.get(), position)
    }

    private fun getShapeAfterMove(shape: Set<XY>, vec: XY): Set<XY> {
        return shape.map{it.add(vec)}.toSet()
    }

    private fun doesShapeFit(shape: Set<XY>, board: Array<IntArray>): Boolean {
        for (cell in shape) {
            if ((!cell.isOnBoard(BOARD_SIZE)) || (board[cell.y][cell.x] != 0)) {
                return false
            }
        }
        return true
    }

    fun move(vec: XY, board: Array<IntArray>): Boolean {
        val newShape = getShapeAfterMove(shapeSwitcher.get(),position.add(vec))
        if (!doesShapeFit(newShape, board)) {
            return false
        }
        position = position.add(vec)
        return true
    }
    fun fall(board: Array<IntArray>): Boolean {
        // returns false if piece stopped while falling.
        // New piece should be assigned in this case
        val newShape = getShapeAfterMove(shapeSwitcher.get(),position.add(DOWN))
        if (!doesShapeFit(newShape, board)) {
            stop(board)
            return false
        }
        position = position.add(DOWN)
        return true
    }
    fun rotate(board: Array<IntArray>) {
        val newShape = getShapeAfterMove(shapeSwitcher.getNext(), position)
        if (doesShapeFit(newShape, board)) {
            shapeSwitcher.switch()
        }
    }
    fun stop(board: Array<IntArray>) {
        for (cell in getActualShape()) {
            if (board[cell.y][cell.x] != 0) {
                throw IllegalStateException("$cell is already occupied")
            }
            board[cell.y][cell.x] = imageIndex
        }
    }
}