package com.mertrizakaradeniz.animalquiz.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.mertrizakaradeniz.animalquiz.R


class GameOverActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "GameOverActivity"
    }

    private lateinit var animationConfetti: LottieAnimationView
    private lateinit var animationFinish: LottieAnimationView
    private lateinit var btnPlayAgain: Button
    private lateinit var tvScore: TextView
    private lateinit var tvResult: TextView
    private lateinit var tvFirstScore: TextView
    private lateinit var tvSecondScore: TextView
    private lateinit var tvThirdScore: TextView

    private var score = 0
    private var firstScore = 0
    private var secondScore = 0
    private var thirdScore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_game_over)

        animationConfetti = findViewById(R.id.animationConfetti)
        animationFinish = findViewById(R.id.animationFinish)
        btnPlayAgain = findViewById(R.id.btnPlayAgain)
        tvScore = findViewById(R.id.tvScore)
        tvResult = findViewById(R.id.tvResult)
        tvFirstScore = findViewById(R.id.tvFirstScore)
        tvSecondScore = findViewById(R.id.tvSecondScore)
        tvThirdScore = findViewById(R.id.tvThirdScore)

        score = intent.getIntExtra("score", 0)
        Log.d(TAG, "$score")
        tvScore.text = "Score: $score"

        if (score == 75) {
            tvResult.text = "YOU WON"
        }

        setupSharedPreferences()

        tvFirstScore.text = "1- $firstScore"
        tvSecondScore.text = "2- $secondScore"
        tvThirdScore.text = "3- $thirdScore"
    }

    private fun setupSharedPreferences() {
        val preference = getSharedPreferences(
            resources.getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
        val editor = preference.edit()
        firstScore = preference.getInt("first", 0)
        secondScore = preference.getInt("second", 0)
        thirdScore = preference.getInt("third", 0)
        if (thirdScore > firstScore) {
            editor.putInt("first", thirdScore)
            editor.putInt("third", firstScore)
        } else if (secondScore > firstScore) {
            editor.putInt("first", secondScore)
            editor.putInt("second", firstScore)
        } else if (thirdScore > secondScore) {
            editor.putInt("second", thirdScore)
            editor.putInt("third", secondScore)
        }
        val min = minOf(firstScore, secondScore, thirdScore)
        if (score > min) {
            if (firstScore == min) {
                editor.putInt("first", score)
            } else if (secondScore == min) {
                editor.putInt("second", score)
            } else if (thirdScore == min) {
                editor.putInt("third", score)
            }
        }
        editor.apply()
    }

    fun playAgain_OnClick(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("playAgain", true)
        startActivity(intent)
        finish()
    }
}