package com.example.cocktailapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
        binding = ActivityCocktailDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val cocktailId = intent.getStringExtra("cocktail_id")

        binding.ingredientsDetail.visibility = View.GONE

        tabLayout = binding.detailTab
        tabLayout.addOnTabSelectedListener(this)

        if (cocktailId != null) {
            search(cocktailId)
        }

        binding.detailTopAppBar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun search(query: String) {
        searchService.fetchData(ApiUrls.URL_COCKTAIL_DETAIL, query) { drinksResponse ->
            drinksResponse?.let {
                updateUI(it)
            }
        }
    }

    private fun updateUI(drinksResponse: DrinksResponse) {
        runOnUiThread {
            val drink = drinksResponse.drinks?.get(0)

            Log.i(
                "DETAIL",
                "[${drink?.id}] category of ${drink?.title}: ${drink?.category}, tags: ${drink?.tags}, alcohol: ${drink?.alcoholic}"
            )

            Picasso.get().load(drink?.imageURL).into(binding.cocktailImage)

            binding.cocktailName.text = drink?.title
            binding.cocktailCategory.text =
                getString(R.string.cocktail_detail_category, drink?.category)
            binding.textInstructionDetail.text = drink?.instructions

            val tagContainerLayout = binding.tagsContainer

            if (drink?.tags != null) {
                val tagList = drink.tags!!.split(',')

                for (tag in tagList) {
                    val cardView = layoutInflater.inflate(
                        R.layout.cocktail_detail_tag, tagContainerLayout, false
                    ) as LinearLayout
                    cardView.findViewById<TextView>(R.id.tag_name).text = tag
                    tagContainerLayout.addView(cardView)
                }
            } else {
                val cardView = layoutInflater.inflate(
                    R.layout.cocktail_detail_tag, tagContainerLayout, false
                ) as LinearLayout
                cardView.findViewById<TextView>(R.id.tag_name).text =
                    getString(R.string.cocktail_detail_no_tag)
                tagContainerLayout.addView(cardView)
            }

            when (drink?.alcoholic) {
                "Alcoholic" -> binding.cocktailName.setCompoundDrawablesWithIntrinsicBounds(
                    null, null, ContextCompat.getDrawable(this, R.drawable.alcohol), null
                )

                "Non alcoholic" -> binding.cocktailName.setCompoundDrawablesWithIntrinsicBounds(
                    null, null, ContextCompat.getDrawable(this, R.drawable.no_alcohol), null
                )

                "Optional alcohol" -> binding.cocktailName.setCompoundDrawablesWithIntrinsicBounds(
                    null, null, ContextCompat.getDrawable(this, R.drawable.optional_alcohol), null
                )

                else -> binding.cocktailName.setCompoundDrawablesWithIntrinsicBounds(
                    null, null, ContextCompat.getDrawable(this, R.drawable.alcohol), null
                )
            }

            val glassContainerLayout = binding.glassContainer
            val cardView = layoutInflater.inflate(
                R.layout.cocktail_detail_tag, tagContainerLayout, false
            ) as LinearLayout
            cardView.findViewById<TextView>(R.id.tag_name).text = getString(R.string.cocktail_detail_glass, drink?.glass)
            glassContainerLayout.addView(cardView)

            ingredientAdapter = IngredientAdapter(
                drink?.getIngredients() ?: emptyList(), drink?.getMeasures() ?: emptyList()
            )

            binding.ingredientRecyclerView.adapter = ingredientAdapter
            binding.ingredientRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        }
    }

    private fun displayTab(isInstruction: Boolean) {
        binding.ingredientsDetail.visibility = if (isInstruction) {
            View.GONE
        } else {
            View.VISIBLE
        }
        binding.instructionDetail.visibility = if (!isInstruction) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun onTabChange(tab: TabLayout.Tab) {
        when (tab.position) {
            0 -> displayTab(true)
            1 -> displayTab(false)
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        tab?.let {
            onTabChange(it)
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {}

    override fun onTabReselected(tab: TabLayout.Tab?) {
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