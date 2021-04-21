package com.mertrizakaradeniz.animalquiz.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
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
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var fabSound: FloatingActionButton
    private lateinit var tvQuestions: TextView
    private lateinit var rvOptionsBoard: RecyclerView
    private lateinit var animationSuccess: LottieAnimationView
    private lateinit var animationFail: LottieAnimationView
    private lateinit var adapter: OptionsBoardAdapter
    private lateinit var tts: TextToSpeech

    private var boardSize = BoardSize.LEVEL_1
    private var qPosition = 0
    private var question = ArrayList<Animal>()
    private var cards = ArrayList<Question>()
    private var answer = -1
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the view elements
        tvScore = findViewById(R.id.tvScore)
        toolbar = findViewById(R.id.toolbar)
        fabSound = findViewById(R.id.fabSound)
        tvQuestions = findViewById(R.id.tvQuestion)
        rvOptionsBoard = findViewById(R.id.rvOptionsBoard)
        animationSuccess = findViewById(R.id.animationSuccess)
        animationFail = findViewById(R.id.animationFail)
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
                speakOut(cards[qPosition].questionText.toString())
            }
        } else Log.i(TAG, "Initialization Failed!")
    }

    override fun onDestroy() {
        tts.stop()
        tts.shutdown()
        super.onDestroy()
    }

    private fun animate(animationView: LottieAnimationView) {
        animationView.visibility = View.VISIBLE
        animationView.playAnimation()
    }

    private fun speakOut(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_ADD, null, "")
    }

    private fun setupOptionsBoard() {
        adapter = OptionsBoardAdapter(this, boardSize, object : CardClickListener {
            override fun onCardClicked(position: Int) {
                if (qPosition == 2 && position == answer) {
                    when (boardSize) {
                        BoardSize.LEVEL_1 -> boardSize = BoardSize.LEVEL_2
                        BoardSize.LEVEL_2 -> boardSize = BoardSize.LEVEL_3
                        BoardSize.LEVEL_3 -> boardSize = BoardSize.LEVEL_4
                        BoardSize.LEVEL_4 -> boardSize = BoardSize.LEVEL_5
                        BoardSize.LEVEL_5 -> Log.i(TAG, "YOU WON")
                    }
                    score += 5
                    tvScore.setText("Score: $score")
                    speakOut("You got it!")
                    animate(animationSuccess)
                    Handler().postDelayed({
                        setupQuestion()
                        setupOptionsBoard()
                        speakOut(cards[qPosition].questionText.toString())
                        qPosition = 0
                        animationSuccess.visibility = View.GONE
                    }, 2000)
                } else if (position == answer) {
                    qPosition++
                    score += 5
                    tvScore.setText("Score: $score")
                    speakOut("You got it!")
                    animate(animationSuccess)
                    Handler().postDelayed({
                        setupQuestion()
                        adapter.notifyDataSetChanged()
                        speakOut(cards[qPosition].questionText.toString())
                        animationSuccess.visibility = View.GONE
                    }, 2000)
                    Log.i(TAG, "Answer is correct $answer")
                } else if (position != answer) {
                    speakOut("The answer is wrong!")
                    speakOut("Game Over")
                    animate(animationFail)
                    Handler().postDelayed({
                        animationSuccess.visibility = View.GONE
                        val intent = Intent(this@MainActivity, GameOverActivity::class.java)
                        startActivity(intent)
                        finish()
                    }, 2000)
                }
            }
        })
        adapter.updateData(question)
        rvOptionsBoard.adapter = adapter
        rvOptionsBoard.setHasFixedSize(true)
        rvOptionsBoard.layoutManager = GridLayoutManager(this, boardSize.getWidth())
    }

    private fun setupQuestion() {
        when (boardSize) {
            BoardSize.LEVEL_1 -> {
                //supportActionBar!!.setTitle("Level 1")
                cards.clear()
                question.clear()
                cards.add(Q1Lvl1)
                cards.add(Q2Lvl1)
                cards.add(Q3Lvl1)
                tvQuestions.setText(cards[qPosition].questionText)
                question.add(cards[qPosition].animalOne!!)
                question.add(cards[qPosition].animalTwo!!)
                answer = cards[qPosition].correctAnswer!!
            }
            BoardSize.LEVEL_2 -> {
                //supportActionBar!!.setTitle("Level 2")
                cards.clear()
                question.clear()
                cards.add(Q1Lvl2)
                cards.add(Q2Lvl2)
                cards.add(Q3Lvl2)
                tvQuestions.setText(cards[qPosition].questionText)
                question.add(cards[qPosition].animalOne!!)
                question.add(cards[qPosition].animalTwo!!)
                question.add(cards[qPosition].animalThree!!)
                answer = cards[qPosition].correctAnswer!!
            }
            BoardSize.LEVEL_3 -> {
                //supportActionBar!!.setTitle("Level 3")
                cards.clear()
                question.clear()
                cards.add(Q1Lvl3)
                cards.add(Q2Lvl3)
                cards.add(Q3Lvl3)
                tvQuestions.setText(cards[qPosition].questionText)
                question.add(cards[qPosition].animalOne!!)
                question.add(cards[qPosition].animalTwo!!)
                question.add(cards[qPosition].animalThree!!)
                question.add(cards[qPosition].animalFour!!)
                answer = cards[qPosition].correctAnswer!!
            }
            BoardSize.LEVEL_4 -> {
                //supportActionBar!!.setTitle("Level 4")
                cards.clear()
                question.clear()
                cards.add(Q1Lvl4)
                cards.add(Q2Lvl4)
                cards.add(Q3Lvl4)
                tvQuestions.setText(cards[qPosition].questionText)
                question.add(cards[qPosition].animalOne!!)
                question.add(cards[qPosition].animalTwo!!)
                question.add(cards[qPosition].animalThree!!)
                question.add(cards[qPosition].animalFour!!)
                question.add(cards[qPosition].animalFive!!)
                question.add(cards[qPosition].animalSix!!)
                answer = cards[qPosition].correctAnswer!!
            }
            BoardSize.LEVEL_5 -> {
                //supportActionBar!!.setTitle("Level 5")
                cards.clear()
                question.clear()
                cards.add(Q1Lvl5)
                cards.add(Q2Lvl5)
                cards.add(Q3Lvl5)
                tvQuestions.setText(cards[qPosition].questionText)
                question.add(cards[qPosition].animalOne!!)
                question.add(cards[qPosition].animalTwo!!)
                question.add(cards[qPosition].animalThree!!)
                question.add(cards[qPosition].animalFour!!)
                question.add(cards[qPosition].animalFive!!)
                question.add(cards[qPosition].animalSix!!)
                question.add(cards[qPosition].animalSeven!!)
                question.add(cards[qPosition].animalEight!!)
                answer = cards[qPosition].correctAnswer!!
            }
        }
    }

    fun fab_OnClick(view: View) {
        speakOut(cards[qPosition].questionText.toString())
    }
}