package com.example.cocktailapp.ui.ingredients

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailapp.core.model.CategoriesResponse
import com.example.cocktailapp.core.model.Category
import com.example.cocktailapp.core.model.Ingredient
import com.example.cocktailapp.core.model.IngredientsResponse
import com.example.cocktailapp.databinding.ItemListBinding
import com.example.cocktailapp.ui.categories.CategoryViewHolder

class IngredientViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
    val titleTextView: TextView = binding.listTitleTextview
    val iconView: ImageView = binding.listIcon
}
class IngredientsAdapter(private var ingredientList: IngredientsResponse): RecyclerView.Adapter<IngredientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient: Ingredient? = ingredientList.ingredients?.get(position)
        holder.titleTextView.text = ingredient?.name ?: ""
        var resId: Int = holder.iconView.context.resources.getIdentifier("@drawable/lemon", null, holder.iconView.context.packageName)
        holder.iconView.setImageResource(resId)
    }

    override fun getItemCount(): Int {
        return ingredientList.ingredients?.count() ?: 0
    }

    fun updateData(newIngredients: IngredientsResponse) {
        ingredientList =  newIngredients
        notifyDataSetChanged() // Actualise la vue pour refléter les nouvelles données
    }

}