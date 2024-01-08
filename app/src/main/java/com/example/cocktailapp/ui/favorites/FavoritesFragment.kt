package com.example.cocktailapp.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cocktailapp.DataStoreUtils
import com.example.cocktailapp.core.model.ApiUrls
import com.example.cocktailapp.core.model.Drink
import com.example.cocktailapp.core.service.SearchDrinkFetcher
import com.example.cocktailapp.databinding.FragmentSearchBinding
import com.example.cocktailapp.ui.search.SearchAdapter
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [FavoritesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val drinkFetcher = SearchDrinkFetcher()
    private var allFavoritesLength: Int = 0
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.circularProgressIndicator.visibility = View.VISIBLE
        binding.noFavView.visibility = View.INVISIBLE
        binding.noResultView.visibility = View.INVISIBLE
        binding.cocktailRecyclerView.visibility = View.INVISIBLE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun loadData(allFavorites: Set<Preferences.Key<*>>?){
        lifecycleScope.launch {
            binding.circularProgressIndicator.visibility = View.VISIBLE
            val drinks = allFavorites?.mapNotNull { key ->
                fetchDrink(key)
            }.orEmpty()
            updateUI(drinks)
        }
    }

    private suspend fun fetchDrink(key: Preferences.Key<*>): Drink? {
        val drinksResponse = drinkFetcher.fetchDataWithWaiting(ApiUrls.URL_COCKTAIL_DETAIL, key.toString())
        return drinksResponse?.drinks?.firstOrNull()
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

    private fun updateUI(drinks: List<Drink>?) {
        activity?.runOnUiThread {
            val drinkList = drinks ?: emptyList()
            searchAdapter = SearchAdapter(drinkList)
            binding.cocktailRecyclerView.adapter = searchAdapter
            val gridLayoutManager = GridLayoutManager(context, 2)
            binding.cocktailRecyclerView.layoutManager = gridLayoutManager
            binding.circularProgressIndicator.visibility = View.GONE
            if (drinkList.isEmpty()) {
                binding.noFavView.visibility = View.VISIBLE
            } else {
                binding.cocktailRecyclerView.visibility = View.VISIBLE
            }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = FavoritesFragment()
    }
}