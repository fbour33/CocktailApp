package com.example.cocktailapp.ui.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.cocktailapp.core.model.Category
import com.example.cocktailapp.databinding.ItemListBinding

class CategoryViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
    val titleTextView: TextView = binding.listTitleTextview
}

class CategoryAdapter(private var categories: List<Category>): RecyclerView.Adapter<CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.titleTextView.text = category.name
    }

    override fun getItemCount(): Int {
        return categories.count()
    }

    fun updateData(newCategories: List<Category>) {
        categories = newCategories
        notifyDataSetChanged() // Actualise la vue pour refléter les nouvelles données
    }
}