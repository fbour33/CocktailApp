package com.example.cocktailapp.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocktailapp.core.model.ApiUrls
import com.example.cocktailapp.core.model.Drink
import com.example.cocktailapp.core.service.SearchDrinkFetcher
import com.example.cocktailapp.databinding.FragmentSearchBinding


/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val searchService = SearchDrinkFetcher()
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        search("")

        return binding.root
    }

    fun search(query: String) {
        binding.noResultView.visibility = View.INVISIBLE
        binding.cocktailRecyclerView.visibility = View.INVISIBLE
        binding.circularProgressIndicator.visibility = View.VISIBLE
        binding.noFavView.visibility = View.INVISIBLE
        searchService.fetchData(ApiUrls.URL_COCKTAIL_SEARCH, query) { drinksResponse ->
            drinksResponse?.let {
                updateUI(it.drinks)
            }
        }
    }

    private fun updateUI(drinks: List<Drink>?) {
        activity?.runOnUiThread {
            val drinkList = drinks ?: emptyList()
            searchAdapter = SearchAdapter(drinkList)
            binding.cocktailRecyclerView.adapter = searchAdapter
            val gridLayoutManager = GridLayoutManager(context, 2)
            binding.cocktailRecyclerView.layoutManager = gridLayoutManager
            binding.circularProgressIndicator.visibility = View.GONE
            if (drinkList.isEmpty()) {
                binding.noResultView.visibility = View.VISIBLE
            } else {
                binding.cocktailRecyclerView.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        /**
         * @return A new instance of fragment SearchFragment.
         */
        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}