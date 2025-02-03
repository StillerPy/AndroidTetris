package ru.stillercode.logic

data class XY(val x: Int, val y: Int) {
    fun add(v: XY): XY {
        return XY(x + v.x, y + v.y)
    }
    fun isOnBoard(size: XY): Boolean {
        return (x in 0..<size.x) && (y in 0..<size.y)
    }
    override fun toString(): String {
        return "<$x, $y>"
    }
}
