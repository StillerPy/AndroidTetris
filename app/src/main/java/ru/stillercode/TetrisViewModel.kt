package ru.stillercode

import androidx.lifecycle.ViewModel
import ru.stillercode.logic.LEFT
import ru.stillercode.logic.RIGHT
import ru.stillercode.logic.Tetris
import kotlin.concurrent.thread

class TetrisViewModel: ViewModel() {
    private var isLeftHeld = false
    private var isRightHeld = false
    private var isRotateHeld = false
    private var isRunning = true
    private var tetris = Tetris()
    fun newGame() {
        tetris = Tetris()
    }
    fun pause() {
        isRunning = !isRunning
        thread {
            while (isRunning) {
                Thread.sleep(300)
                tetris.fall()
            }
        }
    }
    fun getPauseButtonText(): String {
        return if (isRunning) "Pause" else "Run"
    }
    fun getBoardArrayToDraw(): Array<IntArray> {
        return tetris.getBoardArrayToDraw()
    }
    fun getNextPieceArrayToDraw(): Array<IntArray> {
        return tetris.getNextPieceArrayToDraw()
    }
    fun left() {
        tetris.move(LEFT)
    }
    fun right() {
        tetris.move(RIGHT)
    }
    fun rotate() {
        tetris.rotate()
    }
}