package com.example.cocktailapp.ui.random

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocktailapp.ActivityCocktailDetail
import com.example.cocktailapp.core.model.ApiUrls
import com.example.cocktailapp.core.model.DrinksResponse
import com.example.cocktailapp.core.service.SearchDrinkFetcher
import com.example.cocktailapp.databinding.FragmentRandomCocktailBinding
import com.example.cocktailapp.ui.cocktails.FragmentType
import com.example.cocktailapp.ui.ingredients.IngredientsAdapter
import com.example.cocktailapp.ui.ingredients.IngredientsFragment

/**
 * A simple [Fragment] subclass.
 * Use the [RandomCocktailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RandomCocktailFragment : Fragment() {

    private lateinit var binding: FragmentRandomCocktailBinding
    private val cocktailFetcher = SearchDrinkFetcher()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRandomCocktailBinding.inflate(inflater, container, false)
        binding.circularProgressIndicator.visibility = View.GONE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.randomButton.setOnClickListener {
            it.visibility = View.GONE
            binding.circularProgressIndicator.visibility = View.VISIBLE
            cocktailFetcher.fetchData(ApiUrls.URL_COCKTAIL_RANDOM) { ingredientResponse ->
                ingredientResponse?.let {
                    updateIngredient(it)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.circularProgressIndicator.visibility = View.GONE
        binding.randomButton.visibility = View.VISIBLE
    }

    private fun updateIngredient(cocktailResponse: DrinksResponse){
        activity?.runOnUiThread {
            binding.circularProgressIndicator.visibility = View.GONE
            if(cocktailResponse.drinks?.isNotEmpty() == true) {
                val drink = cocktailResponse.drinks?.get(0)
                val intent = Intent(context, ActivityCocktailDetail::class.java)
                intent.putExtra("cocktail_id", drink?.id)
                context?.startActivity(intent)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = RandomCocktailFragment()
    }
}