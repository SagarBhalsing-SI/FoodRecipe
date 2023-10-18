package com.example.foodrecipe.ui.fragments.ingredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipe.R
import com.example.foodrecipe.adapters.IngredientsAdapter
import com.example.foodrecipe.models.Result
import com.example.foodrecipe.util.Constants.Companion.RECIPE_RESULT_KEY

class IngredientsFragment : Fragment() {

    private val mAdapter : IngredientsAdapter by lazy {IngredientsAdapter()}
    private lateinit var ingredientsRecyclerView : RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view  = inflater.inflate(R.layout.fragment_ingredients, container, false)
        ingredientsRecyclerView= view.findViewById(R.id.ingredients_recyclerview)
        val args = arguments
        val myBundle: Result? = args?.getParcelable(RECIPE_RESULT_KEY)
        setupRecyclerView(view)
        myBundle?.extendedIngredients?.let{mAdapter.setData(it) }
        return view
    }

    private fun setupRecyclerView(view:View){
        ingredientsRecyclerView.adapter = mAdapter
        ingredientsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

}