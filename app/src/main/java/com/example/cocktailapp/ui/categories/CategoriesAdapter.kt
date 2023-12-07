package com.example.cocktailapp.ui.categories

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.cocktailapp.core.model.CategoriesResponse
import com.example.cocktailapp.core.model.Category
import com.example.cocktailapp.core.model.Drink
import com.example.cocktailapp.core.model.DrinksResponse
import com.example.cocktailapp.databinding.ItemListBinding

class CategoryViewHolder(
    private val binding: ItemListBinding,
) : RecyclerView.ViewHolder(binding.root) {
    val titleTextView: TextView = binding.listTitleTextview
    val iconView: ImageView = binding.listIcon
}

class CategoryAdapter(
    private var categoryList: DrinksResponse,
    private val onItemClick: (String) -> Unit
): RecyclerView.Adapter<CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category: Drink? = categoryList.drinks?.get(position)
        holder.titleTextView.text = category?.category ?: ""
        var resId: Int = holder.iconView.context.resources.getIdentifier("@drawable/tag", null, holder.iconView.context.packageName)
        holder.iconView.setImageResource(resId)
        holder.itemView.setOnClickListener {
            val categoryName = category?.category ?: ""
            onItemClick(categoryName) // Appel du callback avec le nom de la catégorie
        }
    }

    override fun getItemCount(): Int {
        return categoryList.drinks?.count() ?: 0
    }
}