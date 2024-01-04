package com.example.cocktailapp.ui.favorites

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cocktailapp.DataStoreUtils
import com.example.cocktailapp.core.model.ApiUrls
import com.example.cocktailapp.core.model.Drink
import com.example.cocktailapp.core.model.DrinksResponse
import com.example.cocktailapp.core.service.SearchDrinkFetcher
import com.example.cocktailapp.databinding.FragmentSearchBinding
import com.example.cocktailapp.ui.search.SearchAdapter
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import java.util.concurrent.CompletableFuture

/**
 * A simple [Fragment] subclass.
 * Use the [FavoritesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val drinkFetcher = SearchDrinkFetcher()
    private val drinksTemp = mutableListOf<Drink>()
    private var allFavoritesLength: Int = 0
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
        binding.circularProgressIndicator.visibility = View.VISIBLE
        binding.noResultView.visibility = View.INVISIBLE
        binding.cocktailRecyclerView.visibility = View.INVISIBLE
        return binding.root
    }

    private fun loadData(allFavorites: Set<Preferences.Key<*>>?){
        binding.circularProgressIndicator.visibility = View.VISIBLE
        allFavorites?.let {it ->
            val drinksTemp = it.map { key ->
                drinkFetcher.fetchDataWithWaiting(ApiUrls.URL_COCKTAIL_DETAIL, key.toString()).thenApply { response ->
                    response?.drinks?.get(0)
                }
            }
            val allFutures = CompletableFuture.allOf(*drinksTemp.toTypedArray())
            allFutures.join()
            val drinks = drinksTemp.mapNotNull { it.get() }
            updateUI(drinks)
        }
    }

    override fun onResume() {
        super.onResume()
        val applicationContext = requireContext().applicationContext
        lifecycleScope.launch {
            val allFavorites = DataStoreUtils.getAllFavorites(applicationContext)
            if (allFavorites != null) {
                if(allFavorites.size != allFavoritesLength)
                    allFavoritesLength = allFavorites.size
                loadData(allFavorites)
            }
        }
    }


    //override fun onResume() {
    //    super.onResume()
    //    binding.noResultView.visibility = View.INVISIBLE
    //    binding.cocktailRecyclerView.visibility = View.INVISIBLE
    //    binding.circularProgressIndicator.visibility = View.VISIBLE
    //    val applicationContext = requireContext().applicationContext
    //    lifecycleScope.launch {
    //        val allFavorites = DataStoreUtils.getAllFavorites(applicationContext)
    //        Log.d("FAVORITES", "All favorites: $allFavorites")
    //        allFavorites?.let {it ->
    //            val drinksTemp = it.map { key ->
    //                drinkFetcher.fetchDataWithWaiting(ApiUrls.URL_COCKTAIL_DETAIL, key.toString()).thenApply { response ->
    //                    response?.drinks?.get(0)
    //                }
    //            }
    //            val allFutures = CompletableFuture.allOf(*drinksTemp.toTypedArray())
    //            allFutures.join()
    //            val drinks = drinksTemp.mapNotNull { it.get() }
    //            updateUI(drinks)
    //        }
    //    }
    //}

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
         * @return A new instance of fragment CategoriesFragment.
         */
        @JvmStatic
        fun newInstance() = FavoritesFragment()
    }
}