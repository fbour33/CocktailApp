package com.example.cocktailapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailapp.databinding.SearchItemBinding

class SearchAdapter(private val nameList: List<String>)
    : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = SearchItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun getItemCount() = nameList.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        with(holder){
            with(nameList[position]) {
                binding.searchNameTextView.text = this
            }
        }
    }

    inner class SearchViewHolder(val binding: SearchItemBinding)
        :RecyclerView.ViewHolder(binding.root)

}