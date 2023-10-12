package com.example.foodrecipe.ui.fragments.recipe

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipe.R
import com.example.foodrecipe.viewmodels.MainViewModel
import com.example.foodrecipe.adapters.RecipesAdapter
import com.example.foodrecipe.databinding.FragmentRecipiesBinding
import com.example.foodrecipe.ui.observeOnce
import com.example.foodrecipe.util.Constants.Companion.API_KEY
import com.example.foodrecipe.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.foodrecipe.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.foodrecipe.util.Constants.Companion.DEFAULT_RECIPES_NUMBER
import com.example.foodrecipe.util.NetworkResult
import com.example.foodrecipe.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipiesFragment : Fragment() {

    private val args by navArgs<RecipiesFragmentArgs>()
    private var _binding: FragmentRecipiesBinding? = null
    private val binding get() = _binding!!
    private val mAdapter by lazy { RecipesAdapter() }

    //private lateinit var mView: View
    private var recyclerView: RecyclerView? = null

    private var loader: ProgressBar? = null
    private val mainViewModel: MainViewModel by viewModels()
    private val recipesViewModel: RecipesViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecipiesBinding.inflate(inflater, container, false)
        Log.i("RecipiesFragment", "onCreateView")
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        recyclerView = view?.findViewById(R.id.rv_recipes)
        loader = view?.findViewById(R.id.loader)
        setupRecyclerView()
        //requestApiData()
        readDatabase()

        binding.recipesFab.setOnClickListener{
            findNavController().navigate(R.id.action_recipiesFragment_to_recipesBottomSheet)
        }
        return binding.root
    }

   /* override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rv_recipes)
        loader = view.findViewById(R.id.loader)
        setupRecyclerView()
        //requestApiData()
        readDatabase()

        binding.recipesFab.setOnClickListener{
            findNavController().navigate(R.id.action_recipiesFragment_to_recipesBottomSheet)
        }
    }*/

    private fun setupRecyclerView() {
        val adapter = mAdapter
        recyclerView?.adapter = adapter
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner, { database ->
                if (database.isNotEmpty() && !args.backFromBottomSheet) {
                    database.getOrNull(0)?.results?.let { mAdapter.setData(it) }
                    hideLoader()
                } else {
                    requestApiData()
                }
            })
        }
    }

    private fun requestApiData() {
        Log.d("RecipesFragment", "readDatabase caled!")
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        //mainViewModel.getRecipes(applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner, { response ->
            Log.i("RECIPE DATA RESPONSE:", response.toString())
            when (response) {
                is NetworkResult.Success -> {
                    hideLoader()
                    response.data?.let { mAdapter.setData(it) }
                }

                is NetworkResult.Error -> {
                    hideLoader()
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is NetworkResult.Loading -> {
                    showLoaderEffect()
                }

            }
        })
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner, { database ->
                database
                if (database.isNotEmpty()) {
                    database.getOrNull(0)?.results?.let { mAdapter.setData(it) }
                }
            })
        }
    }

   /* private fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries["number"] = DEFAULT_RECIPES_NUMBER
        queries["apiKey"] = API_KEY
        queries["type"] = DEFAULT_MEAL_TYPE
        queries["diet"] = DEFAULT_DIET_TYPE
        queries["addRecipeInformation"] = "true"
        queries["fillIngredients"] = "true"

        return queries
    }*/


    private fun hideLoader() {
        loader?.isVisible = false
    }

    private fun showLoaderEffect() {
        loader?.isVisible = true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding =null
    }
}