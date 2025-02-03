package ru.stillercode

import android.os.Bundle
import android.widget.Button
import androidx.gridlayout.widget.GridLayout
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.stillercode.logic.BOARD_SIZE
import ru.stillercode.logic.PIECE_MAX_DIM
import ru.stillercode.logic.XY
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val boardCells = createGrid(R.id.play_field, BOARD_SIZE)
        val nextPieceCells = createGrid(R.id.next_piece_display, PIECE_MAX_DIM)
        val viewModel: TetrisViewModel by viewModels()
        findViewById<Button>(R.id.new_game_button).setOnClickListener {
            viewModel.newGame()
        }
        findViewById<Button>(R.id.right_button).setOnClickListener {
            viewModel.right()
        }
        findViewById<Button>(R.id.left_button).setOnClickListener {
            viewModel.left()
        }
        findViewById<Button>(R.id.rotate_button).setOnClickListener {
            viewModel.rotate()
        }
        findViewById<Button>(R.id.pause_button).setOnClickListener {
            viewModel.pause()
        }
        thread {
            while (true) {
                updateVisuals(boardCells, nextPieceCells, viewModel)
                Thread.sleep(100)
            }
        }

    }
    private fun createGrid(gridLayoutId: Int, size: XY): List<List<ImageView>> {
        val gridLayout = findViewById<GridLayout>(gridLayoutId)
        val out = mutableListOf<MutableList<ImageView>>()
        for (y in 0 until size.y) {
            val line = mutableListOf<ImageView>()
            for (x in 0 until size.x) {
                val cell = ImageView(this).apply {
                    //setBackgroundResource(R.drawable.cell)
                    layoutParams = androidx.gridlayout.widget.GridLayout.LayoutParams().apply {
                        width = 0
                        height = 0
                        columnSpec = androidx.gridlayout.widget.GridLayout.spec(x, 1, 1f)
                        rowSpec = androidx.gridlayout.widget.GridLayout.spec(y, 1, 1f)
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
                if (valueArray[y][x] == 0) {
                    cellGrid[y][x].setBackgroundResource(R.drawable.cell)
                } else {
                    //TODO
                    cellGrid[y][x].setBackgroundResource(R.drawable.livecell)
                }
            }
        }
    }
    private fun updateVisuals(
        boardCells: List<List<ImageView>>,
        nextPieceCells: List<List<ImageView>>,
        viewModel: TetrisViewModel
    ) {
        println("Update")
        updateGrid(boardCells, viewModel.getBoardArrayToDraw(), BOARD_SIZE)
        updateGrid(nextPieceCells, viewModel.getNextPieceArrayToDraw(), PIECE_MAX_DIM)
        findViewById<Button>(R.id.pause_button).text = viewModel.getPauseButtonText()
    }
}