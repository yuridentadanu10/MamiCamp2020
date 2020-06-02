package com.yuridentadanu.mamicamp2020.FragmentAndActivity.Game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Menu
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.yuridentadanu.mamicamp2020.R

class GameActivity : AppCompatActivity() {

    internal var score =0

    internal lateinit var btnTapMe: Button
    internal lateinit var tvGameScore: TextView
    internal lateinit var tvTimer: TextView

    internal var gameStarted = false

    internal lateinit var countDownTimer: CountDownTimer
    internal val initCountdown: Long = 60000
    internal val countDownnInterval: Long = 1000
    internal var timeLeftOnTimer: Long = 6000
    companion object{
        private val TAG = GameActivity::class.java.simpleName

        private const val SCORE_KEY = "SCORE_KEY"
        private const val TIME_LEFT_KEY="TIME_LEFT_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        Log.d(TAG, "onCreate called, Score is: $score")
        btnTapMe= findViewById(R.id.btn_tapMe)
        tvGameScore = findViewById(R.id.tv_yourScore)
        tvTimer = findViewById(R.id.tv_timeLeft)

        btnTapMe.setOnClickListener{view ->
            val bounceAnimation = AnimationUtils.loadAnimation(this,
                R.anim.bounce
            )
            view.startAnimation(bounceAnimation)
            incrementScore()
        }
        tvGameScore.text = getString(R.string.your_score,0)
        tvTimer.text = getString(R.string.time_left,0)


        if(savedInstanceState!= null){
            score =savedInstanceState.getInt(SCORE_KEY)
            timeLeftOnTimer = savedInstanceState.getLong(TIME_LEFT_KEY)
            restoreGame()
        }else
        {
            resetGame()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    private fun restoreGame(){
        tvGameScore.text= getString(R.string.your_score,score)
        val restoredTime = timeLeftOnTimer/1000
        tvTimer.text = getString(R.string.time_left,restoredTime)

        countDownTimer = object: CountDownTimer(timeLeftOnTimer,countDownnInterval){
            override fun onFinish() {
                endGame()
            }

            override fun onTick(millisUntilFinished: Long) {
                timeLeftOnTimer=millisUntilFinished
                val timeLeft = millisUntilFinished /1000
                tvTimer.text = getString(R.string.time_left,timeLeft)
            }

        }

        countDownTimer.start()
        gameStarted = true
    }

    private fun resetGame(){
        score = 0
        tvGameScore.text = getString(R.string.your_score,score)
        countDownTimer = object : CountDownTimer(initCountdown,countDownnInterval){
            override fun onFinish() {
                endGame()
            }

            override fun onTick(millisUntilFinished: Long) {
                timeLeftOnTimer=millisUntilFinished
                val timeLeft = millisUntilFinished /1000
                tvTimer.text = getString(R.string.time_left,timeLeft)
            }

        }
        gameStarted = false
    }

    private fun incrementScore() {

        if(!gameStarted){
            startGame()
        }

        score += 1
        val newScore = getString(R.string.your_score,score)
        tvGameScore.text=newScore

        val blinkAnimation = AnimationUtils.loadAnimation(this,
            R.anim.blink
        )
        tvGameScore.startAnimation(blinkAnimation)
    }

    private fun startGame(){
        countDownTimer.start()
        gameStarted = true
    }

    private fun endGame(){
        Toast.makeText(this,getString(R.string.end_game,score), Toast.LENGTH_LONG).show()
        resetGame()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(SCORE_KEY,score)
        outState.putLong(TIME_LEFT_KEY,timeLeftOnTimer)
        countDownTimer.cancel()

        Log.d(TAG,"onSaveInstanced: Saving score $score & timeLeft $timeLeftOnTimer")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"on Destroy called")
    }
}
