package com.mertrizakaradeniz.animalquiz.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mertrizakaradeniz.animalquiz.R
import com.mertrizakaradeniz.animalquiz.adapter.OptionsBoardAdapter
import com.mertrizakaradeniz.animalquiz.models.Animal
import com.mertrizakaradeniz.animalquiz.models.BoardSize
import com.mertrizakaradeniz.animalquiz.models.Question
import com.mertrizakaradeniz.animalquiz.utils.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var tvScore: TextView
    private lateinit var fabSound: FloatingActionButton
    private lateinit var tvQuestions: TextView
    private lateinit var rvOptionsBoard: RecyclerView
    private lateinit var adapter: OptionsBoardAdapter
    private lateinit var tts: TextToSpeech

    private var boardSize = BoardSize.LEVEL_1
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
        tts = TextToSpeech(this, this)

        setupQuestion()
        setupOptionsBoard()

    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.i(TAG, "The Language specified is not supported!")
            } else {
                speakOut(cards[Qposition].questionText.toString())
            }
        } else Log.i(TAG, "Initialization Failed!")
    }

    override fun onDestroy() {
        tts.stop()
        tts.shutdown()
        super.onDestroy()
    }

    private fun speakOut(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_ADD, null, "")
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
                    score += 5
                    tvScore.setText("Score: $score")
                    speakOut("You got it!")
                    setupQuestion()
                    setupOptionsBoard()
                    Thread.sleep(1000)
                    Qposition = 0
                    speakOut(cards[Qposition].questionText.toString())
                }
                else if (position == answer) {
                    Qposition++
                    Log.i(TAG, "$Qposition")
                    score += 5
                    tvScore.setText("Score: $score")
                    speakOut("You got it!")
                    setupQuestion()
                    adapter.notifyDataSetChanged()
                    Thread.sleep(1000)
                    speakOut(cards[Qposition].questionText.toString())
                    Log.i(TAG, "Answer is correct $answer")
                } else if (position != answer) {
                    Log.i(TAG, "Answer is not correct $answer")
                    speakOut("The answer is wrong!")
                    Toast.makeText(this@MainActivity, "Game Over!!", Toast.LENGTH_SHORT).show()
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

    fun fab_OnClick(view: View) {
        speakOut(cards[Qposition].questionText.toString())
    }
}