package com.yuridentadanu.mamicamp2020.FragmentAndActivity.Game

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yuridentadanu.mamicamp2020.BuildConfig
import com.yuridentadanu.mamicamp2020.Const
import com.yuridentadanu.mamicamp2020.Const.DB_HISTORY
import com.yuridentadanu.mamicamp2020.Const.DB_NAME
import com.yuridentadanu.mamicamp2020.Const.DB_USERS
import com.yuridentadanu.mamicamp2020.R
import com.yuridentadanu.mamicamp2020.model.HistoryGame
import com.yuridentadanu.mamicamp2020.Const.getUidUser
import com.yuridentadanu.mamicamp2020.FragmentAndActivity.HistoryGame.HistoryActivity
import com.yuridentadanu.mamicamp2020.FragmentAndActivity.Leaderboard.LeaderboardActivity
import com.yuridentadanu.mamicamp2020.model.Leaderboard
import com.yuridentadanu.mamicamp2020.model.User
import java.text.SimpleDateFormat
import java.util.*

class GameActivity : AppCompatActivity() {

    internal var score =0

    internal lateinit var btnTapMe: Button
    internal lateinit var btnHistory: Button
    internal lateinit var btnLeaderboard: Button
    internal lateinit var tvGameScore: TextView
    internal lateinit var tvTimer: TextView
    internal var gameStarted = false

    private lateinit var db: FirebaseFirestore

    internal lateinit var countDownTimer: CountDownTimer
    internal val initCountdown: Long = 20000
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
        db = Firebase.firestore
        btnTapMe= findViewById(R.id.btn_tapMe)
        tvGameScore = findViewById(R.id.tv_yourScore)
        tvTimer = findViewById(R.id.tv_timeLeft)
        btnHistory=findViewById(R.id.btn_history)
        btnLeaderboard =findViewById(R.id.btn_leaderboard)

        btnTapMe.setOnClickListener{view ->
            val bounceAnimation = AnimationUtils.loadAnimation(this,
                R.anim.bounce
            )
            view.startAnimation(bounceAnimation)
            incrementScore()
        }
        btnHistory.setOnClickListener{ view ->
            startActivity(Intent(this, HistoryActivity::class.java))
        }
        btnLeaderboard.setOnClickListener{ view ->
            startActivity(Intent(this, LeaderboardActivity::class.java))
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.actionAbout) {
            showInfo()
        }
        else if (item.itemId == R.id.LogOut) {
            Firebase.auth.signOut()
        }

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
        writeScoretoDB()
        writeScoretoLeaderboard(score.toLong())
        resetGame()
    }


    private fun showInfo() {
        val dialogTitle = getString(R.string.aboutTitle)
        val dialogMessage = getString(R.string.aboutMessage)

        val builder = AlertDialog.Builder(this)
        builder.setTitle(dialogTitle)
        builder.setMessage(dialogMessage)
        builder.create().show()
    }

    private fun writeScoretoDB(){

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date()).toString()
        val user = HistoryGame(currentDate , score.toLong())
        val uid = getUidUser()
        db.collection(Const.DB_USERS).document(uid).collection(DB_HISTORY).add(user)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!")
              }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    private fun writeScoretoLeaderboard(score: Long){

        val uid = getUidUser()
        val docRef = db.collection(DB_USERS).document(uid)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                   val name = document.getString(DB_NAME)
                    val user = Leaderboard(name, score)
                    db.collection(Const.DB_LEADERBOARD).add(user)
                        .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written! $score")
                        }
                        .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

                    Log.d(TAG, "DocumentSnapshot data: ${document.getString(DB_NAME)}")
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }


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
