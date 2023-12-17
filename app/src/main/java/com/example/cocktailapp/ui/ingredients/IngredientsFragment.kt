package com.example.cocktailapp.ui.ingredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocktailapp.core.model.IngredientsResponse
import com.example.cocktailapp.core.service.IngredientsFetcher
import com.example.cocktailapp.databinding.FragmentIngredientsBinding

/**
 * A simple [Fragment] subclass.
 * Use the [IngredientsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class IngredientsFragment : Fragment() {

    private lateinit var binding: FragmentIngredientsBinding
    private lateinit var adapter: IngredientsAdapter
    private val categoriesFetcher: IngredientsFetcher = IngredientsFetcher()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIngredientsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = IngredientsAdapter(IngredientsResponse())
        binding.recyclerViewIngredient.visibility = View.INVISIBLE
        binding.circularProgressIndicator.visibility = View.VISIBLE
        categoriesFetcher.fetchData { ingredientResponse ->
            ingredientResponse?.let {
                updateIngredient(it)
            }
        }
    }

    private fun updateIngredient(ingredientsResponse: IngredientsResponse) {
        activity?.runOnUiThread {
            adapter = IngredientsAdapter(ingredientsResponse)
            binding.recyclerViewIngredient.adapter = adapter
            binding.recyclerViewIngredient.layoutManager = LinearLayoutManager(context)
            binding.circularProgressIndicator.visibility = View.GONE
            val isCategoryListNotEmpty = ingredientsResponse.ingredients?.isNotEmpty() ?: false
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