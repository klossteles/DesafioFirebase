package com.klossteles.desafiofirebase.games.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.klossteles.desafiofirebase.R
import com.klossteles.desafiofirebase.games.model.GameModel

class GameListAdapter(private val _dataset: List<GameModel>, private val listener: (GameModel) ->Unit) :
    RecyclerView.Adapter<GameListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_games_list_item, parent, false)
        return GameListViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameListViewHolder, position: Int) {
        val item = _dataset[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { listener(item) }
    }

    override fun getItemCount() = _dataset.size

}