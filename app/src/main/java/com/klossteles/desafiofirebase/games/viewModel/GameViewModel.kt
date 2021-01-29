package com.klossteles.desafiofirebase.games.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.klossteles.desafiofirebase.games.model.GameModel
import com.klossteles.desafiofirebase.games.repository.GamesRepository

class GameViewModel (
    private val _repository: GamesRepository
): ViewModel() {
    val games = MutableLiveData<MutableList<GameModel>>()

    fun saveGame(name: String, description: String, createdAt: Int) {
        val game = GameModel("", name, description, createdAt, "")
        _repository.saveGame(game)
    }

    fun getGames() {
        _repository.getGames { 
            games.value = it
        }
    }


    class GameViewModelFactory(private val _repository: GamesRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return GameViewModel(_repository) as T
        }
    }
}