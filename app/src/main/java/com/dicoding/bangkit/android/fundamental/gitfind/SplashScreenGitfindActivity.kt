package com.dicoding.bangkit.android.fundamental.gitfind

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.dicoding.bangkit.android.fundamental.gitfind.Activity.MainActivity
import java.lang.Exception

class SplashScreenGitfindActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //hilangin action bar splashscreen
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.activity_splash_screen_gitfind)


        setContentView(R.layout.activity_splash_screen_gitfind)

        val bekron = object : Thread(){
            override fun run() {
                try {
                    Thread.sleep(3000L)
                    val intensplash = Intent(baseContext, MainActivity::class.java)
                    startActivity(intensplash)
                }catch (e : Exception){
                    e.printStackTrace()
                }
            }
        }
        bekron.start()
    }
}