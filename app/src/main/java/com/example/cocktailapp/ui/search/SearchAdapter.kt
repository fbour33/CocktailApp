package com.example.cocktailapp.ui.search

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailapp.core.model.Drink
import com.example.cocktailapp.core.model.DrinksResponse
import com.example.cocktailapp.databinding.SearchItemBinding
import com.squareup.picasso.Picasso

class SearchAdapter(private val cocktailList: DrinksResponse) :
    RecyclerView.Adapter<SearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = SearchItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun getItemCount() = cocktailList.drinks?.count() ?: 0

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val drink: Drink? = cocktailList.drinks?.get(position)
        holder.titleTextView.text = drink?.title ?: ""
        Picasso.get().load(drink?.imageURL).into(holder.imageView)
        holder.cardContainer.setOnClickListener {
            Log.d("CARD", "Cocktail ${drink?.title} clicked" )
        }
    }


}

class SearchViewHolder(val binding: SearchItemBinding) : RecyclerView.ViewHolder(binding.root) {
    val titleTextView: TextView = binding.cocktailName
    val imageView: ImageView = binding.cocktailImage
    val cardContainer: CardView = binding.searchCard
}
