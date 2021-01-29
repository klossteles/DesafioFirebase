package com.klossteles.desafiofirebase.games.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.klossteles.desafiofirebase.R
import org.w3c.dom.Text

class GameDetailsFragment : Fragment() {

    private lateinit var _view: View
    private var _description: String = ""
    private var _name: String = ""
    private var _createdAt: Int?= null
    private var _id: String = ""
    private var _imgUrl: String= ""

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

        loadValues()
    }

    private fun loadValues() {
        _description = arguments?.getString(GamesListFragment.DESCRIPTION).toString()
        _name = arguments?.getString(GamesListFragment.NAME).toString()
        _createdAt = arguments?.getInt(GamesListFragment.CREATED_AT)
        _id = arguments?.getString(GamesListFragment.ID).toString()
        _imgUrl = arguments?.getString(GamesListFragment.IMG_URL).toString()

        _view.findViewById<TextView>(R.id.txtGameTitleDescription).text = _name
        _view.findViewById<TextView>(R.id.txtGameCreatedAtDescription).text = _createdAt.toString()
        _view.findViewById<TextView>(R.id.txtGameDescriptionDetails).text = _description
    }

    private fun onEditGame(navController: NavController) {
        _view.findViewById<FloatingActionButton>(R.id.btnEditGame).setOnClickListener {
            val bundle = bundleOf(
                GamesListFragment.CREATED_AT to _createdAt,
                GamesListFragment.NAME to _name,
                GamesListFragment.DESCRIPTION to _description,
                GamesListFragment.IMG_URL to _imgUrl,
                GamesListFragment.ID to _id
            )
            navController.navigate(R.id.action_gameDetailsFragment_to_gameRegisterFragment, bundle)
        }
    }

    private fun onBack(navController: NavController) {
        _view.findViewById<ImageView>(R.id.imgBack).setOnClickListener {
            navController.navigateUp()
        }
    }

}