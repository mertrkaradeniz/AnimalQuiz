package com.mertrizakaradeniz.animalquiz.view

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
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
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var tvTime: TextView
    private lateinit var tvScore: TextView
    private lateinit var tvLevel: TextView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var fabSound: FloatingActionButton
    private lateinit var tvQuestions: TextView
    private lateinit var rvOptionsBoard: RecyclerView
    private lateinit var lottieAnimation: LottieAnimationView
    private lateinit var loadingAnimation: LottieAnimationView
    private lateinit var layoutRoot: ConstraintLayout
    private lateinit var layoutAnimation: ConstraintLayout
    private lateinit var adapter: OptionsBoardAdapter
    private lateinit var tts: TextToSpeech
    private lateinit var timer: CountDownTimer

    private var boardSize = BoardSize.LEVEL_1
    private var qPosition = 0
    private var question = ArrayList<Animal>()
    private var cards = ArrayList<Question>()
    private var answer = -1
    private var score = 0
    private var playAgain = false
    private var duration: Long = TimeUnit.MINUTES.toMillis(2) + 5000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the view elements
        tvTime = findViewById(R.id.tvTime)
        tvScore = findViewById(R.id.tvScore)
        tvLevel = findViewById(R.id.tvLevel)
        toolbar = findViewById(R.id.toolbar)
        fabSound = findViewById(R.id.fabSound)
        tvQuestions = findViewById(R.id.tvQuestion)
        rvOptionsBoard = findViewById(R.id.rvOptionsBoard)
        lottieAnimation = findViewById(R.id.lottieAnimation)
        loadingAnimation = findViewById(R.id.loadingAnimation)
        layoutRoot = findViewById(R.id.layoutRoot)
        layoutAnimation = findViewById(R.id.layoutAnimation)
        tts = TextToSpeech(this, this)

        setupQuestion()
        setupOptionsBoard()
        setupTimer()

        playAgain = intent.getBooleanExtra("playAgain", false)
        if (!playAgain) {
            Handler().postDelayed({
                layoutRoot.isVisible = true
                layoutAnimation.isVisible = false
                loadingAnimation.pauseAnimation()
            }, 4000)
        } else {
            Handler().postDelayed({
            layoutRoot.isVisible = true
            layoutAnimation.isVisible = false
            loadingAnimation.pauseAnimation()
            }, 2000)
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.i(TAG, "The Language specified is not supported!")
            } else {
                if (!playAgain) {
                    Handler().postDelayed({
                        speakOut(cards[qPosition].questionText.toString())
                    }, 3000)
                    Handler().postDelayed({
                        playSound()
                    }, 5000)
                } else {
                    speakOut(cards[qPosition].questionText.toString())
                    Handler().postDelayed({
                        playSound()
                    }, 5000)
                }
            }
        } else Log.i(TAG, "Initialization Failed!")
    }

    override fun onDestroy() {
        tts.stop()
        tts.shutdown()
        super.onDestroy()
    }

    private fun setupTimer() {
        timer = object : CountDownTimer(duration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tvTime.text = "${(millisUntilFinished / 1000).toInt()} s"
            }
            override fun onFinish() {
                speakOut("Time's up")
                animate(lottieAnimation, R.raw.times_up)
                Handler().postDelayed({
                    lottieAnimation.visibility = View.GONE
                    val intent = Intent(this@MainActivity, GameOverActivity::class.java)
                    intent.putExtra("score", score)
                    startActivity(intent)
                    finish()
                }, 2000)
            }
        }
        timer.start()
    }

    private fun animate(animationView: LottieAnimationView, id: Int) {
        animationView.visibility = View.VISIBLE
        animationView.setAnimation(id)
        animationView.playAnimation()
    }

    private fun speakOut(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_ADD, null, "")
    }

    private fun makeSound(soundId: Int) {
        var mediaPlayer = MediaPlayer.create(this, soundId)
        mediaPlayer.start()
        Handler().postDelayed({
            mediaPlayer?.release()
            mediaPlayer = null
        }, 2000)
    }

    private fun playSound() {
        val correctId = cards[qPosition].correctAnswer
        when (correctId) {
            0 -> makeSound(cards[qPosition].animalOne!!.sound)
            1 -> makeSound(cards[qPosition].animalTwo!!.sound)
            2 -> makeSound(cards[qPosition].animalThree!!.sound)
            3 -> makeSound(cards[qPosition].animalFour!!.sound)
            4 -> makeSound(cards[qPosition].animalFive!!.sound)
            5 -> makeSound(cards[qPosition].animalSix!!.sound)
            6 -> makeSound(cards[qPosition].animalSeven!!.sound)
            7 -> makeSound(cards[qPosition].animalEight!!.sound)
        }
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
                        BoardSize.LEVEL_5 -> {
                            speakOut("YOU WON")
                            animate(lottieAnimation, R.raw.congratulation)
                            Handler().postDelayed({
                                lottieAnimation.visibility = View.GONE
                                val intent = Intent(this@MainActivity, GameOverActivity::class.java)
                                intent.putExtra("score", score)
                                startActivity(intent)
                                finish()
                            }, 2500)
                            score += 5
                            tvScore.setText("Score: $score")
                            return
                        }
                    }
                    answerCorrect()
                    Handler().postDelayed({
                        setupOptionsBoard()
                    }, 2000)
                    qPosition = 0
                } else if (position == answer) {
                    qPosition++
                    answerCorrect()
                } else if (position != answer) {
                    speakOut("The answer is wrong!")
                    speakOut("Game Over")
                    animate(lottieAnimation, R.raw.failed)
                    Handler().postDelayed({
                        lottieAnimation.visibility = View.GONE
                        val intent = Intent(this@MainActivity, GameOverActivity::class.java)
                        intent.putExtra("score", score)
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

    private fun answerCorrect() {
        score += 5
        tvScore.setText("Score: $score")
        speakOut("You got it!")
        animate(lottieAnimation, R.raw.successful)
        Handler().postDelayed({
            setupQuestion()
            adapter.notifyDataSetChanged()
            lottieAnimation.visibility = View.GONE
            speakOut(cards[qPosition].questionText.toString())
            Handler().postDelayed({
                playSound()
            }, 1000)
        }, 2000)
        Log.i(TAG, "Answer is correct $answer")
    }

    private fun setupQuestion() {
        when (boardSize) {
            BoardSize.LEVEL_1 -> {
                tvLevel.text = "Level 1"
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
                tvLevel.text = "Level 2"
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
                tvLevel.text = "Level 3"
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
                tvLevel.text = "Level 4"
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
                tvLevel.text = "Level 5"
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
        Handler().postDelayed({
            playSound()
        }, 1000)
    }

    fun restart_OnClick(view: View) {
        recreate()
    }
}