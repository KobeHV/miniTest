package com.example.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login_success.*

class LoginSuccess : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_success)

        addFunction()
    }
    private fun addFunction():Boolean{
        button_getpaper.setOnClickListener(){
            val intent = Intent(this, Paper::class.java)
            startActivity(intent)
        }
        return true
    }
}


