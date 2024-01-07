package com.example.cocktailapp.ui.cocktails

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cocktailapp.core.model.ApiUrls
import com.example.cocktailapp.core.model.Drink
import com.example.cocktailapp.core.service.SearchDrinkFetcher
import com.example.cocktailapp.databinding.FragmentCocktailBinding
import com.example.cocktailapp.ui.search.SearchAdapter
import java.lang.IllegalArgumentException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val CATEGORY_NAME = "CATEGORY_NAME"
private const val FRAGMENT_NAME = "FRAGMENT_NAME"

/**
 * A simple [Fragment] subclass.
 * Use the [CocktailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CocktailFragment : Fragment() {
    private var categoryName: String? = null
    private var fragmentName: FragmentType? = null

    private val cocktailService = SearchDrinkFetcher()
    private lateinit var cocktailAdapter: SearchAdapter

    private var _binding: FragmentCocktailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            categoryName = it.getString(CATEGORY_NAME)
            try {
                fragmentName = it.getString(FRAGMENT_NAME)?.let { it1 -> FragmentType.valueOf(it1) }
            }catch (e : IllegalArgumentException){
                Log.e("Instantiate error", e.toString())
            }
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

        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.noResultView.visibility = View.INVISIBLE
        binding.cocktailRecyclerView.visibility = View.INVISIBLE
        binding.circularProgressIndicator.visibility = View.VISIBLE
        var callURL: ApiUrls = ApiUrls.URL_COCKTAIL_FILTER_INGREDIENT
        if(fragmentName == FragmentType.CATEGORY)
            callURL = ApiUrls.URL_COCKTAIL_FILTER_CATEGORY
        categoryName?.let { it ->
            cocktailService.fetchData(callURL, it) { drinksResponse ->
                drinksResponse?.let {
                    it.drinks?.let { it1 -> updateUI(it1) }
                }
            }
        }
    }

    private fun updateUI(drinks: List<Drink>) {
        activity?.runOnUiThread {
            val drinkList = drinks?: emptyList()
            cocktailAdapter = SearchAdapter(drinkList)
            binding.cocktailRecyclerView.adapter = cocktailAdapter
            val gridLayoutManager = GridLayoutManager(context, 2)
            binding.cocktailRecyclerView.layoutManager = gridLayoutManager
            binding.circularProgressIndicator.visibility = View.GONE
            if (drinkList.isEmpty()){
                binding.noResultView.visibility = View.VISIBLE
            }else{
                binding.cocktailRecyclerView.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(categoryName: String, fragmentName: FragmentType) =
            CocktailFragment().apply {
                arguments = Bundle().apply {
                    putString(CATEGORY_NAME, categoryName)
                    putString(FRAGMENT_NAME, fragmentName.name)
                }
            }
    }
}