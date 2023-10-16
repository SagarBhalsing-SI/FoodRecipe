package com.example.foodrecipe.data
import com.example.foodrecipe.models.FoodRecipe
import com.example.foodrecipe.data.network.FoodRecipesApi
import retrofit2.Response
import javax.inject.Inject
class RemoteDataSource @Inject constructor (
    private val foodRecipesApi: FoodRecipesApi
) {
    suspend fun getRecipes(queries:Map<String, String>):Response<FoodRecipe>{
       return foodRecipesApi.getRecipes(queries)

    }

    suspend fun searchRecipes(searchQuery: Map<String,String>):Response<FoodRecipe>{
        return foodRecipesApi.searchRecipes(searchQuery)
    }
}