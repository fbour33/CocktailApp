package com.example.cocktailapp.ui.ingredients

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocktailapp.core.model.ApiUrls
import com.example.cocktailapp.core.model.DrinksResponse
import com.example.cocktailapp.core.service.SearchDrinkFetcher
import com.example.cocktailapp.databinding.FragmentIngredientsBinding
import com.example.cocktailapp.ui.categories.CategoryListener
import com.example.cocktailapp.ui.cocktails.FragmentType

/**
 * A simple [Fragment] subclass.
 * Use the [IngredientsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class IngredientsFragment : Fragment() {

    private lateinit var binding: FragmentIngredientsBinding
    private lateinit var adapter: IngredientsAdapter
    private lateinit var listener: CategoryListener
    private val categoriesFetcher = SearchDrinkFetcher()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CategoryListener) {
            listener = context
        } else {
            throw RuntimeException("Must implement AnswersListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIngredientsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding.recyclerViewIngredient.visibility = View.INVISIBLE
        binding.circularProgressIndicator.visibility = View.VISIBLE
        categoriesFetcher.fetchData(ApiUrls.URL_INGREDIENT_LIST) { ingredientResponse ->
            ingredientResponse?.let{
                updateIngredient(it)
            }
        }
    }

    private fun updateIngredient(ingredientsResponse: DrinksResponse){
        activity?.runOnUiThread {
            adapter = IngredientsAdapter(ingredientsResponse) { ingredientName ->
                Log.d("CARD", "Ingredient $ingredientName clicked")
                listener.onSelected(ingredientName, FragmentType.INGREDIENT)
            }
            binding.recyclerViewIngredient.adapter = adapter
            binding.recyclerViewIngredient.layoutManager = LinearLayoutManager(context)
            binding.circularProgressIndicator.visibility = View.GONE
            val isCategoryListNotEmpty = ingredientsResponse.drinks?.isNotEmpty() ?: false
            binding.recyclerViewIngredient.visibility =
                if (isCategoryListNotEmpty) View.VISIBLE else View.INVISIBLE
        }
    }

    companion object {
        /**
         * @return A new instance of fragment IngredientsFragment.
         */
        @JvmStatic
        fun newInstance() = IngredientsFragment()
    }
}