package com.example.jiy

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.jiy.FirstFragments.LoginFragment

class MainActivity : AppCompatActivity() {
    private var backPressTime: Long = 0
    private val backPressInterval = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT



        supportFragmentManager.beginTransaction().replace(R.id.container, LoginFragment()).commit()
    }


    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - backPressTime > backPressInterval) {
            backPressTime = currentTime
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()
        } else {
            finish()
        }
    }
}