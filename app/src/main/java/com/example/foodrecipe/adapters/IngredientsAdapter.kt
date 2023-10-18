package com.example.foodrecipe.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.foodrecipe.ExtendedIngredient
import com.example.foodrecipe.R
import com.example.foodrecipe.util.Constants.Companion.BASE_IMAGE_URL
import com.example.foodrecipe.util.RecipesDiffUtil
import java.util.*

class IngredientsAdapter: RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>() {

    lateinit var ingredientImageView : ImageView
    lateinit var ingredientName : TextView
    lateinit var ingredientAmount : TextView
    lateinit var ingredientUnit : TextView
    lateinit var ingredientConsistency : TextView
    lateinit var ingredientOriginal : TextView
    private var ingredientsList = emptyList<ExtendedIngredient>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.ingredients_row_layout, parent, false))
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        ingredientAmount = holder.itemView.findViewById(R.id.ingredient_amount)
        ingredientConsistency = holder.itemView.findViewById(R.id.ingredient_consistency)
        ingredientName = holder.itemView.findViewById(R.id.ingredient_name)
        ingredientOriginal = holder.itemView.findViewById(R.id.ingredient_original)
        ingredientUnit = holder.itemView.findViewById(R.id.ingredient_unit)
        ingredientImageView = holder.itemView.findViewById(R.id.ingredient_imageView)
        ingredientImageView.load(BASE_IMAGE_URL + ingredientsList[position].image) {
            crossfade(600)
            error(R.drawable.ic_error_placeholder)
        }
        ingredientName.text = ingredientsList[position].name.capitalize(Locale.ROOT)
        ingredientAmount.text = ingredientsList[position].amount.toString()
        ingredientUnit.text = ingredientsList[position].unit
        ingredientConsistency.text = ingredientsList[position].consistency
        ingredientOriginal.text = ingredientsList[position].original

    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }

    fun setData(newIngredients: List<ExtendedIngredient>) {
        val ingredientsDiffUtil =
            RecipesDiffUtil(ingredientsList, newIngredients)
        val diffUtilResult = DiffUtil.calculateDiff(ingredientsDiffUtil)
        ingredientsList = newIngredients
        diffUtilResult.dispatchUpdatesTo(this)
    }

}