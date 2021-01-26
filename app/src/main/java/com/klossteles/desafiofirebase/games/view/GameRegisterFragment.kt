package com.klossteles.desafiofirebase.games.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.klossteles.desafiofirebase.R
import com.klossteles.desafiofirebase.games.model.GameModel
import com.klossteles.desafiofirebase.games.viewModel.GameViewModel

class GameRegisterFragment : Fragment() {
    private lateinit var _view: View
    private lateinit var _auth: FirebaseAuth
    private lateinit var _viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _view = view
        _auth = FirebaseAuth.getInstance()

        _viewModel = ViewModelProvider(this, GameViewModel.GameViewModelFactory(FirebaseDatabase.getInstance())
        ).get(GameViewModel::class.java)

        onSaveGame()
        checkCreatedAtChanged()
        checkDescriptionChanged()
        checkNameChanged()
    }

    private fun onSaveGame() {
        _view.findViewById<Button>(R.id.btnSaveGame).setOnClickListener {
            val name =
                _view.findViewById<TextInputEditText>(R.id.edtGameNameRegister)?.text.toString()
            val createdAt =
                _view.findViewById<TextInputEditText>(R.id.edtCreateAtRegister)?.text.toString()
            val description =
                _view.findViewById<TextInputEditText>(R.id.edtDescriptionRegister)?.text.toString()

            var success = true
            if (name.isEmpty()) {
                _view.findViewById<TextInputLayout>(R.id.txtGameNameRegister).error =
                    getString(R.string.name_cannot_be_empty)
                success = false
            }

            if (createdAt.isEmpty()) {
                _view.findViewById<TextInputLayout>(R.id.txtCreateAtRegister).error =
                    getString(R.string.created_at_cannot_be_empty)
                success = false
            }

            if (description.isEmpty()) {
                _view.findViewById<TextInputLayout>(R.id.txtDescriptionRegister).error =
                    getString(R.string.description_cannot_be_empty)
                success = false
            }

            if (!success) {
                return@setOnClickListener
            }
            val user = _auth.currentUser

            if (user != null) {
                _viewModel.saveGame(name, description, createdAt.toInt(), user)
            }
        }
    }

    private fun checkNameChanged() {
        _view.findViewById<TextInputEditText>(R.id.edtGameNameRegister)
            .addTextChangedListener(object :
                TextWatcher {
                override fun afterTextChanged(s: Editable?) { }

                override fun beforeTextChanged( s: CharSequence?, start: Int, count: Int, after: Int ) { }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    _view.findViewById<TextInputLayout>(R.id.txtGameNameRegister).error = ""
                }
            })
    }

    private fun checkCreatedAtChanged() {
        _view.findViewById<TextInputEditText>(R.id.edtCreateAtRegister)
            .addTextChangedListener(object :
                TextWatcher {
                override fun afterTextChanged(s: Editable?) { }

                override fun beforeTextChanged( s: CharSequence?, start: Int, count: Int, after: Int ) { }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    _view.findViewById<TextInputLayout>(R.id.txtCreateAtRegister).error = ""
                }
            })
    }

    private fun checkDescriptionChanged() {
        _view.findViewById<TextInputEditText>(R.id.edtDescriptionRegister)
            .addTextChangedListener(object :
                TextWatcher {
                override fun afterTextChanged(s: Editable?) { }

                override fun beforeTextChanged( s: CharSequence?, start: Int, count: Int, after: Int ) { }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    _view.findViewById<TextInputLayout>(R.id.txtDescriptionRegister).error = ""
                }
            })
    }
}