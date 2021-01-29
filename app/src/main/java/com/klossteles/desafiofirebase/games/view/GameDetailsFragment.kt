package com.klossteles.desafiofirebase.games.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.klossteles.desafiofirebase.R

class GameDetailsFragment : Fragment() {

    private lateinit var _view: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _view = view
        val navController = findNavController()

        onBack(navController)
        onEditGame(navController)
    }

    private fun onEditGame(navController: NavController) {
        _view.findViewById<FloatingActionButton>(R.id.btnEditGame).setOnClickListener {
            navController.navigate(R.id.action_gameDetailsFragment_to_gameRegisterFragment)
        }
    }

    private fun onBack(navController: NavController) {
        _view.findViewById<ImageView>(R.id.imgBack).setOnClickListener {
            navController.navigateUp()
        }
    }

}