package com.klossteles.desafiofirebase.games.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.klossteles.desafiofirebase.R
import com.klossteles.desafiofirebase.games.model.GameModel
import com.squareup.picasso.Picasso

class GameListViewHolder(_view: View): RecyclerView.ViewHolder(_view) {
    private val title = _view.findViewById<TextView>(R.id.txtGameTitle)
    private val gameYear = _view.findViewById<TextView>(R.id.txtGameYear)
    private val image = _view.findViewById<ImageView>(R.id.imgRegister)

    fun bind(gameModel: GameModel) {
        gameYear.text = gameModel.createdAt.toString()
//        Picasso.get().load(gameModel).into(image)
    }
}