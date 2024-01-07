package com.example.cocktailapp.ui.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailapp.core.model.Drink
import com.example.cocktailapp.core.model.DrinksResponse
import com.example.cocktailapp.databinding.ItemListBinding
import com.google.android.material.card.MaterialCardView

class CategoryViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
    val titleTextView: TextView = binding.listTitleTextview
    val iconView: ImageView = binding.listIcon
    val listContainer: MaterialCardView = binding.listCard
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
        holder.listContainer.setOnClickListener {
            val categoryName = category?.category ?: ""
            onItemClick(categoryName) // Callback call with category name
        }
    }

    override fun getItemCount(): Int {
        return categoryList.drinks?.count() ?: 0
    }
}