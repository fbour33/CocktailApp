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
import com.example.cocktailapp.databinding.ItemListBinding

class CategoryViewHolder(
    private val binding: ItemListBinding,
    private val onClickListener: OnClickListener
) : RecyclerView.ViewHolder(binding.root) {
    val titleTextView: TextView = binding.listTitleTextview
    val iconView: ImageView = binding.listIcon

    interface OnClickListener {
        fun onClick(categoryName: String)
    }
}

class CategoryAdapter(
    private var categoryList: CategoriesResponse,
    private val onClickListener: OnClickListener
): RecyclerView.Adapter<CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding, object : CategoryViewHolder.OnClickListener {
            override fun onClick(categoryName: String) = onClickListener.onItemClick(categoryName)
        })
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category: Category? = categoryList.categories?.get(position)
        holder.titleTextView.text = category?.name ?: ""
        var resId: Int = holder.iconView.context.resources.getIdentifier("@drawable/tag", null, holder.iconView.context.packageName)
        holder.iconView.setImageResource(resId)
    }

    override fun getItemCount(): Int {
        return categoryList.categories?.count() ?: 0
    }

    fun updateData(newCategories: CategoriesResponse) {
        categoryList = newCategories
        notifyDataSetChanged() // Actualise la vue pour refléter les nouvelles données
    }

    interface OnClickListener {
        fun onItemClick(categoryName: String)
    }

}