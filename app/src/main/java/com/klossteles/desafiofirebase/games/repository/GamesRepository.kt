package com.klossteles.desafiofirebase.games.repository

import android.util.Log
import com.google.firebase.database.ktx.getValue
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.klossteles.desafiofirebase.games.model.GameModel
import com.klossteles.desafiofirebase.games.view.GameRegisterFragment

class GamesRepository (private val _database: DatabaseReference) {

    fun getGames(callback: (games: MutableList<GameModel>) -> Unit) {
        val games = _database.orderByKey()
        val singleListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val mutableList = mutableListOf<GameModel>()

                for (postSnapshot in snapshot.children) {
                    // TODO: handle the post
                    Log.i("TESTE", postSnapshot.value.toString())
                    mutableList.add(postSnapshot.getValue<GameModel>()!!)
                }
//                snapshot.children.forEach {
//                    val value = it.getValue()
//                    mutableList.add(it.getValue() as GameModel)
//                }
                callback.invoke(mutableList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(GameRegisterFragment.TAG, "ERROR" + error.message)
                callback.invoke(mutableListOf())
            }

        }

        games.addListenerForSingleValueEvent(singleListener)
    }

    fun saveGame(game: GameModel) {
        val newGameRef = _database.push()
        newGameRef.setValue(game)
    }
}