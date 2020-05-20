package com.yuridentadanu.mamicamp2020

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    internal lateinit var btnTapMe: Button
    internal lateinit var tvGameScore: TextView
    internal lateinit var tvTimer: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnTapMe= findViewById(R.id.btn_tapMe)
        tvGameScore = findViewById(R.id.tv_yourScore)
        tvTimer = findViewById(R.id.tv_timeLeft)
        
    }
}
