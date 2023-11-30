package com.example.cocktailapp.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailapp.databinding.IngredientDetailItemBinding

class IngredientAdapter(private val ingredientList: List<String>, private val measureList: List<String>) : RecyclerView.Adapter<SearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = IngredientDetailItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return SearchViewHolder(binding)
    }

    private fun getNbIngredients(): Int{
        var count = 0
        for (i in 0..14){
            if (ingredientList[i].isNotEmpty() || measureList[i].isNotEmpty()) count += 1
        }
        return count
    }

    override fun getItemCount() = getNbIngredients()

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {

        val ingredient: String = if (ingredientList.count() > position) ingredientList[position] else ""
        val measure: String = if (measureList.count() > position) measureList[position] else ""
        val connection = if (measure.isEmpty()) "" else "of"
        val textValue = "$measure $connection $ingredient"
        holder.ingredientTextView.text = textValue
    }


}

class SearchViewHolder(val binding: IngredientDetailItemBinding) : RecyclerView.ViewHolder(binding.root) {
    val ingredientTextView: TextView = binding.textIngredientDetail
}
