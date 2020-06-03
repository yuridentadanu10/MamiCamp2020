package com.yuridentadanu.mamicamp2020.FragmentAndActivity.SplashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.yuridentadanu.mamicamp2020.MainActivity
import com.yuridentadanu.mamicamp2020.R

class SplashScreen : AppCompatActivity() {
    internal lateinit var image: ImageView
    internal lateinit var logo: TextView
    internal lateinit var slogan: TextView
    internal lateinit var topAnim: Animation
    internal lateinit var bottomAnim: Animation
    lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash_screen)


        image = findViewById(R.id.imageView)
        logo = findViewById(R.id.textView)
        slogan = findViewById(R.id.textView2)

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)
//Set animation to elements
        image.setAnimation(topAnim)
        logo.setAnimation(bottomAnim)
        slogan.setAnimation(bottomAnim)


        handler = Handler()
        handler.postDelayed({
            val intent = Intent(this@SplashScreen, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 5000)
    }
}
