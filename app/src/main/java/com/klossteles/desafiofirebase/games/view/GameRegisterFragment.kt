package com.klossteles.desafiofirebase.games.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.klossteles.desafiofirebase.R
import com.klossteles.desafiofirebase.games.repository.GamesRepository
import com.klossteles.desafiofirebase.games.viewModel.GameViewModel

class GameRegisterFragment : Fragment() {
    private lateinit var _view: View
    private lateinit var _auth: FirebaseAuth
    private lateinit var _viewModel: GameViewModel
    private lateinit var _description: String
    private lateinit var _name: String
    private lateinit var _createdAt: String
    private lateinit var _id: String
    private lateinit var _imgUrl: String

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
        val navController = findNavController()
        val user = _auth.currentUser ?: return
        val database = FirebaseDatabase.getInstance().getReference().child("games").child("users").child(user.uid).child("games")

        _viewModel = ViewModelProvider(this, GameViewModel.GameViewModelFactory(GamesRepository(database))
        ).get(GameViewModel::class.java)

        onSaveGame(navController)
        checkCreatedAtChanged()
        checkDescriptionChanged()
        checkNameChanged()
        loadValues()
    }

    private fun onSaveGame(navController: NavController) {
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
                _viewModel.saveGame(name, description, createdAt.toInt())
                navController.navigateUp()
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

    private fun loadValues() {
        _description = arguments?.getString(GamesListFragment.DESCRIPTION).toString()
        _name = arguments?.getString(GamesListFragment.NAME).toString()
        _createdAt = arguments?.getInt(GamesListFragment.CREATED_AT).toString()
        _id = arguments?.getString(GamesListFragment.ID).toString()
        _imgUrl = arguments?.getString(GamesListFragment.IMG_URL).toString()

        _view.findViewById<TextInputEditText>(R.id.edtGameNameRegister).setText(_name)
        _view.findViewById<TextInputEditText>(R.id.edtCreateAtRegister).setText(_createdAt)
        _view.findViewById<TextInputEditText>(R.id.edtDescriptionRegister).setText(_description)
    }

    companion object {
        const val TAG = "GameRegisterFragment"
    }
}