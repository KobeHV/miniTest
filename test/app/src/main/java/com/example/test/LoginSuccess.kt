package com.example.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_login_success.*

class LoginSuccess : AppCompatActivity() {

    var userName:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_success)

        userName = intent.getStringExtra("username")

        if (userName != null) {
            Log.d("exam_app", userName)
        }else{
            Log.d("error","userName is NULL")
        }


        addFunction()
    }

    private fun addFunction(): Boolean {
        button_getpaper.setOnClickListener() {
            val intent = Intent(this, Paper::class.java)
            intent.putExtra("username",userName)
            startActivity(intent)
        }
        return true
    }
}


