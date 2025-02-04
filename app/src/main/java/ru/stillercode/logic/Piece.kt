package ru.stillercode.logic

class Piece(var shape: Set<XY>, val imageIndex: Int) {
    private var rotatingAxis = START_POINT

    fun place() {
        shape = getShapeAfterMove(shape, rotatingAxis.add(ROTATING_POINT))
    }

    fun isPlacedValidly(board: Array<IntArray>): Boolean {
        return doesShapeFit(shape, board)
    }

    fun getShapeToDisplayAsNext(): Set<XY> {
        return shape
    }

    fun getActualShape(): Set<XY> {
        return shape
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
        val newShape = getShapeAfterMove(shape, vec)
        if (!doesShapeFit(newShape, board)) {
            return false
        }
        rotatingAxis = rotatingAxis.add(vec)
        shape = newShape
        return true
    }
    fun fall(board: Array<IntArray>): Boolean {
        // returns false if piece stopped while falling.
        // New piece should be assigned in this case
        val newShape = getShapeAfterMove(shape, DOWN)
        if (!doesShapeFit(newShape, board)) {
            stop(board)
            return false
        }
        rotatingAxis = rotatingAxis.add(DOWN)
        shape = newShape
        return true
    }
    private fun rotateShape(oldShape: Set<XY>, axis: XY): Set<XY> {
        val out = mutableSetOf<XY>()
        for (cell in oldShape) {
            out.add(XY(
                x = axis.x + axis.y - cell.y,
                y = cell.x + axis.x - axis.y
            ))
        }
        return out.toSet()
    }
    fun rotate(board: Array<IntArray>) {
        val newShape = rotateShape(shape, rotatingAxis)
        if (doesShapeFit(newShape, board)) {
            shape = newShape
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