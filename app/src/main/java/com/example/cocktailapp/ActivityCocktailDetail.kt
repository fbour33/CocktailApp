package com.example.cocktailapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocktailapp.core.model.ApiUrls
import com.example.cocktailapp.core.model.DrinksResponse
import com.example.cocktailapp.core.service.SearchDrinkFetcher
import com.example.cocktailapp.databinding.ActivityCocktailDetailBinding
import com.example.cocktailapp.ui.detail.IngredientAdapter
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso


class ActivityCocktailDetail : AppCompatActivity(), TabLayout.OnTabSelectedListener {

    private lateinit var binding: ActivityCocktailDetailBinding
    private val searchService = SearchDrinkFetcher()
    private lateinit var tabLayout: TabLayout
    private lateinit var ingredientAdapter: IngredientAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityCocktailDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val cocktailId = intent.getStringExtra("cocktail_id")

        Log.i("DETAIL", "Detail from $cocktailId")
        binding.ingredientsDetail.visibility = View.GONE

        tabLayout = binding.detailTab
        tabLayout.addOnTabSelectedListener(this)

        if (cocktailId != null) {
            search(cocktailId)
        }

    }

    private fun search(query: String){
        searchService.fetchData(ApiUrls.URL_COCKTAIL_DETAIL, query, true) { drinksResponse ->
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
            ingredientAdapter = IngredientAdapter(drink?.getIngredients() ?: emptyList(), drink?.getMeasures() ?: emptyList())
            binding.ingredientRecyclerView.adapter = ingredientAdapter
            binding.ingredientRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
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