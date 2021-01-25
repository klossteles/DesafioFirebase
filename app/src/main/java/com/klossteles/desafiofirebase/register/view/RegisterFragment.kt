package com.klossteles.desafiofirebase.register.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.klossteles.desafiofirebase.R

class RegisterFragment : Fragment() {
    private lateinit var _view: View
    private lateinit var _auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _view = view
        _auth = FirebaseAuth.getInstance()

        onRegister()
        checkNameChanged()
        checkEmailChanged()
        checkPasswordChanged()
        checkRepeatPasswordChanged()
    }

    private fun onRegister() {
        _view.findViewById<Button>(R.id.btnRegister).setOnClickListener {
            var success = true
            val name = _view.findViewById<TextInputEditText>(R.id.edtNameRegister)?.text.toString()
            if (name.isEmpty()) {
                _view.findViewById<TextInputLayout>(R.id.txtNameRegister).error =
                    getString(R.string.insert_name)
                success = false
            }
            val email = _view.findViewById<TextInputEditText>(R.id.edtEmailRegister)?.text.toString()
            if (email.isEmpty()) {
                _view.findViewById<TextInputLayout>(R.id.txtEmailRegister).error = getString(R.string.insert_email)
                success = false
            }

            val password = _view.findViewById<TextInputEditText>(R.id.edtPasswordRegister)?.text.toString()
            success = checkPassword(password, _view, success)
            success = checkRepeatPassword(password, success)

            if (success) {
                register(email, password, name)
            }
        }
    }

    private fun checkPassword(password: String, view: View, success: Boolean): Boolean {
        var isOk = success
        if (password.isEmpty()) {
            view.findViewById<TextInputLayout>(R.id.txtPasswordRegister).error =
                getString(R.string.password_cannot_be_null)
            isOk = false
        } else if (password.length < 6) {
            view.findViewById<TextInputLayout>(R.id.txtPasswordRegister).error =
                getString(R.string.must_contain_at_least_6_characters)
            isOk = false
        }
        return isOk
    }

    private fun checkRepeatPassword(password: String, success: Boolean): Boolean {
        var isOk = success
        val repeatPassword =
            _view.findViewById<TextInputEditText>(R.id.edtRepeatPasswordRegister)?.text.toString()

        if (repeatPassword.isEmpty()) {
            _view.findViewById<TextInputLayout>(R.id.txtRepeatPasswordRegister).error =
                getString(R.string.password_cannot_be_null)
            isOk = false
        } else if (repeatPassword.length < 6) {
            _view.findViewById<TextInputLayout>(R.id.txtRepeatPasswordRegister).error =
                getString(R.string.must_contain_at_least_6_characters)
            isOk = false
        } else if (repeatPassword != password) {
            _view.findViewById<TextInputLayout>(R.id.txtRepeatPasswordRegister).error = getString(
                R.string.must_be_the_same_as_password
            )
            isOk = false
        }
        return isOk
    }

    private fun checkPasswordChanged() {
        _view.findViewById<TextInputEditText>(R.id.edtPasswordRegister)
            .addTextChangedListener(object :
                TextWatcher {
                override fun afterTextChanged(s: Editable?) {}

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    _view.findViewById<TextInputLayout>(R.id.txtPasswordRegister).error = ""
                }
            })
    }

    private fun checkEmailChanged() {
        _view.findViewById<TextInputEditText>(R.id.edtEmailRegister)
            .addTextChangedListener(object :
                TextWatcher {
                override fun afterTextChanged(s: Editable?) {}

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    _view.findViewById<TextInputLayout>(R.id.txtEmailRegister).error = ""
                }
            })
    }

    private fun checkNameChanged() {
        _view.findViewById<TextInputEditText>(R.id.edtNameRegister)
            .addTextChangedListener(object :
                TextWatcher {
                override fun afterTextChanged(s: Editable?) {}

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    _view.findViewById<TextInputLayout>(R.id.txtNameRegister).error = ""
                }
            })
    }

    private fun checkRepeatPasswordChanged() {
        _view.findViewById<TextInputEditText>(R.id.edtRepeatPasswordRegister)
            .addTextChangedListener(object :
                TextWatcher {
                override fun afterTextChanged(s: Editable?) {}

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    _view.findViewById<TextInputLayout>(R.id.txtRepeatPasswordRegister).error = ""
                    val password =
                        _view.findViewById<TextInputEditText>(R.id.edtPasswordRegister)?.text.toString()
                    checkRepeatPassword(password, true)
                }
            })
    }

    private fun register(email: String, password: String, name: String) {
        _auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = _auth.currentUser
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(
                            name
                        )
                        .build()

                    user!!.updateProfile(profileUpdates).addOnCompleteListener {
                        user.sendEmailVerification()
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(_view.context, getString(R.string.validate_your_email),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Toast.makeText(_view.context, "Erro",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                val navController = findNavController()
                                navController.navigateUp()
                            }
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(_view.context, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}