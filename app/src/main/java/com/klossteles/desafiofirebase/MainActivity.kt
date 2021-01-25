package com.klossteles.desafiofirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    companion object {
        const val LOGIN = "Login"
        const val EMAIL = "Email"
        const val PASSWORD = "Password"
    }
}