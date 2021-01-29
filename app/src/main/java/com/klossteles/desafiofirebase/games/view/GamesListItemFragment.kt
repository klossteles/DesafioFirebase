package com.klossteles.desafiofirebase.games.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.klossteles.desafiofirebase.R
import com.klossteles.desafiofirebase.games.model.GameModel
import com.klossteles.desafiofirebase.games.repository.GamesRepository
import com.klossteles.desafiofirebase.games.viewModel.GameViewModel

class GamesListItemFragment : Fragment() {

    private lateinit var _view:View
    private lateinit var _viewModel: GameViewModel
    private lateinit var _listAdapter: GameListAdapter
    private lateinit var _recyclerView: RecyclerView
    private lateinit var _auth: FirebaseAuth

    private var _games = mutableListOf<GameModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_games_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _view = view

        val manager = GridLayoutManager(_view.context, 2)
        _games = mutableListOf()

        val user = _auth.currentUser ?: return

        _listAdapter = GameListAdapter(_games) {
            val bundle = bundleOf(
                NAME to it.name,
                DESCRIPTION to it.description,
                CREATED_AT to it.createdAt,
            )
            _view.findNavController().navigate(R.id.action_gamesListFragment_to_gameDetailsFragment, bundle)
        }
        _recyclerView = _view.findViewById(R.id.rvGamesList)
        _recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = manager
            adapter = _listAdapter
        }

        _viewModel = ViewModelProvider(
            this,
            GameViewModel.GameViewModelFactory(GamesRepository(FirebaseDatabase.getInstance("games").getReference(user.uid)))
        ).get(GameViewModel::class.java)
    }

    companion object {
        const val DESCRIPTION = "DESCRIPTION"
        const val NAME = "NAME"
        const val CREATED_AT = "CREATED_AT"
    }
}