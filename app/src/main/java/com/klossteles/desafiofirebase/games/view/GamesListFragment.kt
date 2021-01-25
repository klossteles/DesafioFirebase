package com.klossteles.desafiofirebase.games.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.klossteles.desafiofirebase.R

class GamesListFragment : Fragment() {
    private lateinit var _view: View

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
        val navController = findNavController()

        onAddGame(navController)
    }

    private fun onAddGame(navController: NavController) {
        _view.findViewById<FloatingActionButton>(R.id.btnAddGame).setOnClickListener {
            navController.navigate(R.id.action_gamesListFragment_to_gameRegisterFragment)
        }
    }
}