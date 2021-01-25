package com.klossteles.desafiofirebase.login.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.klossteles.desafiofirebase.MainActivity
import com.klossteles.desafiofirebase.R

class LoginFragment : Fragment() {
    private lateinit var _view: View
    private lateinit var _auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _view = view
        _auth = FirebaseAuth.getInstance()
        val navController = findNavController()

        onCreateAccount(navController)
        onLogIn()
    }

    private fun onCreateAccount(navController: NavController) {
        _view.findViewById<Button>(R.id.btnCreateAccount).setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun onLogIn() {
        _view.findViewById<Button>(R.id.btnLogin).setOnClickListener {
            var success = true
            val email = _view.findViewById<TextInputEditText>(R.id.edtEmailLogin)?.text.toString()
            if (email.isEmpty()) {
                _view.findViewById<TextInputLayout>(R.id.txtEmailLogin).error =
                    getString(R.string.insert_email)
                success = false
            }
            val password =
                _view.findViewById<TextInputEditText>(R.id.edtPasswordLogin)?.text.toString()
            if (password.isEmpty()) {
                _view.findViewById<TextInputLayout>(R.id.txtPasswordLogin).error =
                    getString(R.string.insert_password)
                success = false
            }
            if (success) {
                logIn(email, password)
            }
        }
    }

    private fun logIn(email: String, password: String) {
        _auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                if (_view.findViewById<CheckBox>(R.id.checkRemember).isChecked) {
                    val pref = _view.context.getSharedPreferences(MainActivity.LOGIN, Context.MODE_PRIVATE)
                    pref.edit().putString(MainActivity.EMAIL, email).putString(MainActivity.PASSWORD, password).apply()
                } else {
                    val pref = _view.context.getSharedPreferences(MainActivity.LOGIN, Context.MODE_PRIVATE)
                    pref.edit().putString(MainActivity.EMAIL, "").putString(MainActivity.PASSWORD, "").apply()
                }
                val intent = Intent(_view.context, MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            } else {
                Toast.makeText(_view.context, getString(R.string.an_error_has_occurred_check_email_and_password),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}