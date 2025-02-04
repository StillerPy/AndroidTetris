package ru.stillercode

import android.os.Bundle
import android.widget.Button
import androidx.gridlayout.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.stillercode.logic.BOARD_SIZE
import ru.stillercode.logic.LEFT
import ru.stillercode.logic.PIECE_MAX_DIM
import ru.stillercode.logic.RIGHT
import ru.stillercode.logic.Tetris
import ru.stillercode.logic.XY
import java.util.Collections.rotate
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {
    private val cellDrawables = listOf(
        R.drawable.cell_0,
        R.drawable.cell_1,
        R.drawable.cell_2,
        R.drawable.cell_3,
        R.drawable.cell_4,
        R.drawable.cell_5,
        R.drawable.cell_6,
        R.drawable.cell_7)
    private lateinit var boardCells: List<List<ImageView>>
    private lateinit var nextPieceCells: List<List<ImageView>>
    var tetris = Tetris()
    var isRunning = false
    var fallingDelay = 500
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        boardCells = createGrid(R.id.play_field, BOARD_SIZE)
        nextPieceCells = createGrid(R.id.next_piece_display, PIECE_MAX_DIM)
        findViewById<Button>(R.id.new_game_button).setOnClickListener {
            newGame()
        }
        findViewById<Button>(R.id.right_button).setOnClickListener {
            right()
        }
        findViewById<Button>(R.id.left_button).setOnClickListener {
            left()
        }
        findViewById<Button>(R.id.rotate_button).setOnClickListener {
            rotatePiece()
        }
        findViewById<Button>(R.id.pause_button).setOnClickListener {
            pause()
        }
        updateVisuals()
        pause()
    }

    private fun rotatePiece() {
        tetris.rotate()
        updateVisuals()
    }
    private fun updateScore() {
        findViewById<TextView>(R.id.score_text).text = tetris.score.toString()
    }
    private fun updateVisuals() {
        updateGrid(boardCells, tetris.getBoardArrayToDraw(), BOARD_SIZE)
        updateGrid(nextPieceCells, tetris.getNextPieceArrayToDraw(), PIECE_MAX_DIM)
        //updateScore()
    }

    private fun pause() {
        isRunning = !isRunning
        findViewById<Button>(R.id.pause_button).text = if (isRunning) "Pause" else "Run"
        if (isRunning) {
            thread {
                while (isRunning) {
                    fall()
                    Thread.sleep(300)
                }
            }
        }
    }

    private fun fall() {
        tetris.fall()
        updateVisuals()
        if (tetris.isGameOver) {
            newGame()
        }
    }

    private fun left() {
        //if (!isRunning) return
        tetris.move(LEFT)
        updateVisuals()
    }

    private fun right() {
        //if (!isRunning) return
        tetris.move(RIGHT)
        updateVisuals()
    }

    private fun newGame() {
        tetris = Tetris()
        updateVisuals()
    }

    private fun createGrid(gridLayoutId: Int, size: XY): List<List<ImageView>> {
        val gridLayout = findViewById<GridLayout>(gridLayoutId)
        val out = mutableListOf<MutableList<ImageView>>()
        for (y in 0 until size.y) {
            val line = mutableListOf<ImageView>()
            for (x in 0 until size.x) {
                val cell = ImageView(this).apply {
                    //setBackgroundResource(R.drawable.cell)
                    layoutParams = GridLayout.LayoutParams().apply {
                        width = 0
                        height = 0
                        columnSpec = GridLayout.spec(x, 1, 1f)
                        rowSpec = GridLayout.spec(y, 1, 1f)
                    }
                }
                gridLayout.addView(cell)
                line.add(cell)
            }
            out.add(line)
        }
        return out.map{it.toList()}
    }
    private fun updateGrid(cellGrid: List<List<ImageView>>, valueArray: Array<IntArray>, size: XY) {
        for (y in 0 until size.y) {
            for (x in 0 until size.x) {
                val picIndex = valueArray[y][x]
                cellGrid[y][x].setBackgroundResource(cellDrawables[picIndex])
            }
        }
    }
}