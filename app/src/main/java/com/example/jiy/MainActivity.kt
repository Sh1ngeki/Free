package com.example.jiy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jiy.FirstFragments.LoginFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.container, LoginFragment()).commit()
    }
}