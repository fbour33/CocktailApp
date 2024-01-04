package com.example.cocktailapp.ui.favorites

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cocktailapp.DataStoreUtils
import com.example.cocktailapp.core.model.ApiUrls
import com.example.cocktailapp.core.model.Drink
import com.example.cocktailapp.core.service.SearchDrinkFetcher
import com.example.cocktailapp.databinding.FragmentSearchBinding
import com.example.cocktailapp.ui.search.SearchAdapter
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [FavoritesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val drinkFetcher = SearchDrinkFetcher()
    private val drinksTemp = mutableListOf<Drink>()
    private lateinit var searchAdapter: SearchAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val applicationContext = requireContext().applicationContext
        lifecycleScope.launch {
            val allFavorites = DataStoreUtils.getAllFavorites(applicationContext)
            Log.d("FAVORITES", "All favorites: $allFavorites")
            //TODO HAD A VIEW.VISIBLE OF A ANY FAV YET
            allFavorites?.let {
                val deferreds = it.map { key ->
                    async {
                        Log.d("FAVORITES", "Favorite key : $key")
                        drinkFetcher.fetchData(ApiUrls.URL_COCKTAIL_DETAIL, key.toString()) { drinkResponse ->
                            drinkResponse?.drinks?.get(0)?.let { it1 -> drinksTemp.add(it1) }
                            Log.d("DRINKS", "drink added to drinksTemp $drinksTemp")
                        }
                    }
                }
                deferreds.awaitAll()
                updateUI(drinksTemp.toList())
            }
        }
    }

    private fun updateUI(drinks: List<Drink>?) {
        activity?.runOnUiThread {
            val drinkList = drinks ?: emptyList()
            Log.d("DRINKS UI", "drinkList : $drinkList")
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
         * @return A new instance of fragment CategoriesFragment.
         */
        @JvmStatic
        fun newInstance() = FavoritesFragment()
    }
}