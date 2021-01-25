package com.klossteles.desafiofirebase

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.klossteles.desafiofirebase.login.view.LoginActivity

const val SPLASH_DURATION = 2000L
class SplashActivity : AppCompatActivity() {
    private lateinit var _auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        _auth = FirebaseAuth.getInstance()

        Handler(Looper.getMainLooper()).postDelayed({
            val pref = this.getSharedPreferences(MainActivity.LOGIN, Context.MODE_PRIVATE)
            val email = pref.getString(MainActivity.EMAIL, null)
            val password = pref.getString(MainActivity.PASSWORD, null)

            if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
                _auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, getString(R.string.an_error_has_occurred_check_email_and_password),
                                Toast.LENGTH_SHORT
                            ).show()

                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, SPLASH_DURATION)
    }
}