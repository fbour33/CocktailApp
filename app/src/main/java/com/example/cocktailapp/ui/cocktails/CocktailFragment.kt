package com.example.cocktailapp.ui.cocktails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocktailapp.R
import com.example.cocktailapp.core.model.ApiUrls
import com.example.cocktailapp.core.model.DrinksResponse
import com.example.cocktailapp.core.service.SearchDrinkFetcher
import com.example.cocktailapp.databinding.FragmentCategoriesBinding
import com.example.cocktailapp.databinding.FragmentCocktailBinding
import com.example.cocktailapp.ui.search.SearchAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val CATEGORY_NAME = "CATEGORY_NAME"

/**
 * A simple [Fragment] subclass.
 * Use the [CocktailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CocktailFragment : Fragment() {
    private var categoryName: String? = null
    private val cocktailService = SearchDrinkFetcher()
    private lateinit var cocktailAdapter: SearchAdapter

    private var _binding: FragmentCocktailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            categoryName = it.getString(CATEGORY_NAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCocktailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.noResultView.visibility = View.INVISIBLE
        binding.cocktailRecyclerView.visibility = View.INVISIBLE
        binding.circularProgressIndicator.visibility = View.VISIBLE
        categoryName?.let { it ->
            cocktailService.fetchData(ApiUrls.URL_COCKTAIL_SEARCH, it) { drinksResponse ->
                drinksResponse?.let {
                    updateUI(it)
                }
            }
        }
    }

    private fun updateUI(drinksResponse: DrinksResponse) {
        activity?.runOnUiThread {
            cocktailAdapter = SearchAdapter(drinksResponse)
            binding.cocktailRecyclerView.adapter = cocktailAdapter
            binding.cocktailRecyclerView.layoutManager = LinearLayoutManager(context)
            binding.circularProgressIndicator.visibility = View.GONE
            if (drinksResponse.drinks?.isEmpty() != false){
                binding.noResultView.visibility = View.VISIBLE
            }else{
                binding.cocktailRecyclerView.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(categoryName: String) =
            CocktailFragment().apply {
                arguments = Bundle().apply {
                    putString(CATEGORY_NAME, categoryName)
                }
            }
    }
}