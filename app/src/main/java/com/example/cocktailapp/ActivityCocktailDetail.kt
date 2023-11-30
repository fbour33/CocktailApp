package com.example.cocktailapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocktailapp.core.model.DrinksResponse
import com.example.cocktailapp.core.service.SearchDrinkFetcher
import com.example.cocktailapp.databinding.ActivityCocktailDetailBinding
import com.example.cocktailapp.ui.categories.CategoriesFragment
import com.example.cocktailapp.ui.ingredients.IngredientsFragment
import com.example.cocktailapp.ui.search.SearchAdapter
import com.example.cocktailapp.ui.search.SearchFragment
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso


class ActivityCocktailDetail : AppCompatActivity(), TabLayout.OnTabSelectedListener {

    private lateinit var binding: ActivityCocktailDetailBinding
    private val searchService = SearchDrinkFetcher()
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityCocktailDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val cocktailId = intent.getStringExtra("cocktail_id")


        binding.ingredientsDetail.visibility = View.GONE

        tabLayout = binding.detailTab
        tabLayout.addOnTabSelectedListener(this)

        val stringValue = "Id du cocktail cliqué: $cocktailId"
        binding.textIngredientDetail.text = stringValue

        if (cocktailId != null) {
            search(cocktailId)
        }

    }

    private fun search(query: String){
        searchService.fetchData(query, true) {drinksResponse ->
            drinksResponse?.let {
                updateUI(it)
            }
        }
    }

    private fun updateUI(drinksResponse: DrinksResponse) {
        runOnUiThread {
            val drink = drinksResponse.drinks?.get(0)
            Picasso.get().load(drink?.imageURL).into(binding.cocktailImage)
            binding.cocktailName.text = drink?.title
            binding.textInstructionDetail.text = drink?.instructions

        }
    }

    private fun displayTab(isInstruction: Boolean){
        binding.ingredientsDetail.visibility = if (isInstruction) { View.GONE } else { View.VISIBLE }
        binding.instructionDetail.visibility = if (!isInstruction) { View.GONE } else { View.VISIBLE }
    }

    private fun onTabChange(tab: TabLayout.Tab){
        when(tab.position){
            0 -> displayTab(true)
            1 -> displayTab(false)
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        Log.i("Tab", "Selected")
        tab?.let {
            onTabChange(it)
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        Log.i("Tab", "Unselected")
    }
    override fun onTabReselected(tab: TabLayout.Tab?) {
        Log.i("Tab", "Reselected")
        tab?.let {
            onTabChange(it)
        }
    }


    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()

        finish()
    }

}