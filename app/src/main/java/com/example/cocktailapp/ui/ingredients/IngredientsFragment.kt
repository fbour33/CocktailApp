package com.example.cocktailapp.ui.ingredients

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailapp.R
import com.example.cocktailapp.core.model.ApiUrls
import com.example.cocktailapp.core.model.CategoriesResponse
import com.example.cocktailapp.core.model.DrinksResponse
import com.example.cocktailapp.core.model.IngredientsResponse
import com.example.cocktailapp.core.service.CategoriesFetcher
import com.example.cocktailapp.core.service.IngredientsFetcher
import com.example.cocktailapp.core.service.SearchDrinkFetcher
import com.example.cocktailapp.databinding.FragmentCategoriesBinding
import com.example.cocktailapp.databinding.FragmentIngredientsBinding
import com.example.cocktailapp.ui.categories.CategoryAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [IngredientsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class IngredientsFragment : Fragment() {

    private var _binding: FragmentIngredientsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: IngredientsAdapter
    private val categoriesFetcher = SearchDrinkFetcher()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentIngredientsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
            adapter = IngredientsAdapter(ingredientsResponse)
            binding.recyclerViewIngredient.adapter = adapter
            binding.recyclerViewIngredient.layoutManager = LinearLayoutManager(context)
            binding.circularProgressIndicator.visibility = View.GONE
            val isCategoryListNotEmpty = ingredientsResponse.drinks?.isNotEmpty() ?: false
            binding.recyclerViewIngredient.visibility =
                if (isCategoryListNotEmpty) View.VISIBLE else View.INVISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment IngredientsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            IngredientsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}