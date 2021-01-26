package com.klossteles.desafiofirebase.games.viewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.klossteles.desafiofirebase.games.model.GameModel
import kotlinx.coroutines.Dispatchers

class GameViewModel (
    private val _database: FirebaseDatabase
): ViewModel() {
    private var _games: List<GameModel> = listOf()

    fun saveGame(name: String, description: String, createdAt: Int, user: FirebaseUser) {
        val myRef = _database.getReference("games/" + user.uid)
        val game = GameModel(name, description, createdAt)
        val newGameRef = myRef.push()
        newGameRef.setValue(game)
    }

//    fun getList() = liveData(Dispatchers.IO) {
//        val response = _repository.getComics()
//        _games = response.data.results
//        emit(response.data.results)
//    }


    class GameViewModelFactory(private val _database: FirebaseDatabase): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return GameViewModel(_database) as T
        }
    }

    companion object {
        const val TAG = "GameViewModel"
    }
}