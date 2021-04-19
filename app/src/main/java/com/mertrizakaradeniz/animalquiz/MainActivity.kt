package com.mertrizakaradeniz.animalquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mertrizakaradeniz.animalquiz.models.Animal
import com.mertrizakaradeniz.animalquiz.models.BoardSize
import com.mertrizakaradeniz.animalquiz.models.Question
import com.mertrizakaradeniz.animalquiz.utils.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var tvScore: TextView
    private lateinit var fabSound: FloatingActionButton
    private lateinit var tvQuestions: TextView
    private lateinit var rvOptionsBoard: RecyclerView
    private lateinit var adapter: OptionsBoardAdapter

    private var boardSize = BoardSize.LEVEL_4
    private var Qposition = 0
    private var question = ArrayList<Animal>()
    private var cards = ArrayList<Question>()
    private var answer = -1
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the view elements
        tvScore = findViewById(R.id.tvScore)
        fabSound = findViewById(R.id.fabSound)
        tvQuestions = findViewById(R.id.tvQuestion)
        rvOptionsBoard = findViewById(R.id.rvOptionsBoard)

        setupQuestion()
        setupOptionsBoard()
    }

    private fun setupOptionsBoard() {
        adapter = OptionsBoardAdapter(this, boardSize, object : CardClickListener {
            override fun onCardClicked(position: Int) {
                if (Qposition == 2) {
                    if (boardSize == BoardSize.LEVEL_1) boardSize = BoardSize.LEVEL_2
                    else if (boardSize == BoardSize.LEVEL_2) boardSize = BoardSize.LEVEL_3
                    else if (boardSize == BoardSize.LEVEL_3) boardSize = BoardSize.LEVEL_4
                    else if (boardSize == BoardSize.LEVEL_4) boardSize = BoardSize.LEVEL_5
                    else if (boardSize == BoardSize.LEVEL_5) Log.i(TAG, "YOU WON")
                    Qposition = 0
                    setupQuestion()
                    setupOptionsBoard()
                }
                if (position == answer) {
                    Qposition++
                    Log.i(TAG, "$Qposition")
                    score += 5
                    tvScore.setText("Score: $score")
                    setupQuestion()
                    adapter.notifyDataSetChanged()

                } else {
                    Log.i(TAG, "Answer is not correct")
                    score -= 5
                    tvScore.setText("Score: $score")
                }
            }
        })
        adapter.updateData(question)
        rvOptionsBoard.adapter = adapter
        rvOptionsBoard.setHasFixedSize(true) //for efficient
        rvOptionsBoard.layoutManager = GridLayoutManager(this, boardSize.getWidth())
    }

    private fun setupQuestion() {
        when (boardSize) {
            BoardSize.LEVEL_1 -> {
                supportActionBar!!.setTitle("Level 1")
                cards.clear()
                question.clear()
                cards.add(Q1Lvl1)
                cards.add(Q2Lvl1)
                cards.add(Q3Lvl1)
                tvQuestions.setText(cards[Qposition].questionText)
                question.add(cards[Qposition].animalOne!!)
                question.add(cards[Qposition].animalTwo!!)
                answer = cards[Qposition].correctAnswer!!
            }
            BoardSize.LEVEL_2 -> {
                supportActionBar!!.setTitle("Level 2")
                cards.clear()
                question.clear()
                cards.add(Q1Lvl2)
                cards.add(Q2Lvl2)
                cards.add(Q3Lvl2)
                tvQuestions.setText(cards[Qposition].questionText)
                question.add(cards[Qposition].animalOne!!)
                question.add(cards[Qposition].animalTwo!!)
                question.add(cards[Qposition].animalThree!!)
                answer = cards[Qposition].correctAnswer!!
            }
            BoardSize.LEVEL_3 -> {
                supportActionBar!!.setTitle("Level 3")
                cards.clear()
                question.clear()
                cards.add(Q1Lvl3)
                cards.add(Q2Lvl3)
                cards.add(Q3Lvl3)
                tvQuestions.setText(cards[Qposition].questionText)
                question.add(cards[Qposition].animalOne!!)
                question.add(cards[Qposition].animalTwo!!)
                question.add(cards[Qposition].animalThree!!)
                question.add(cards[Qposition].animalFour!!)
                answer = cards[Qposition].correctAnswer!!
            }
            BoardSize.LEVEL_4 -> {
                supportActionBar!!.setTitle("Level 4")
                cards.clear()
                question.clear()
                cards.add(Q1Lvl4)
                cards.add(Q2Lvl4)
                cards.add(Q3Lvl4)
                tvQuestions.setText(cards[Qposition].questionText)
                question.add(cards[Qposition].animalOne!!)
                question.add(cards[Qposition].animalTwo!!)
                question.add(cards[Qposition].animalThree!!)
                question.add(cards[Qposition].animalFour!!)
                question.add(cards[Qposition].animalFive!!)
                question.add(cards[Qposition].animalSix!!)
                answer = cards[Qposition].correctAnswer!!
            }
            BoardSize.LEVEL_5 -> {
                supportActionBar!!.setTitle("Level 5")
                cards.clear()
                question.clear()
                cards.add(Q1Lvl5)
                cards.add(Q2Lvl5)
                cards.add(Q3Lvl5)
                tvQuestions.setText(cards[Qposition].questionText)
                question.add(cards[Qposition].animalOne!!)
                question.add(cards[Qposition].animalTwo!!)
                question.add(cards[Qposition].animalThree!!)
                question.add(cards[Qposition].animalFour!!)
                question.add(cards[Qposition].animalFive!!)
                question.add(cards[Qposition].animalSix!!)
                question.add(cards[Qposition].animalSeven!!)
                question.add(cards[Qposition].animalEight!!)
                answer = cards[Qposition].correctAnswer!!
            }
        }
    }
}