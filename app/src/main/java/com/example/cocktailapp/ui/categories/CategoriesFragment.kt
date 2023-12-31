package com.example.cocktailapp.ui.categories

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocktailapp.core.model.ApiUrls
import com.example.cocktailapp.core.model.DrinksResponse
import com.example.cocktailapp.core.service.SearchDrinkFetcher
import com.example.cocktailapp.databinding.FragmentCategoriesBinding
import com.example.cocktailapp.ui.cocktails.FragmentType


interface CategoryListener {
    fun onSelected(categoryName: String, fragmentName: FragmentType)
}

class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var adapter: CategoryAdapter
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
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewCategory.visibility = View.INVISIBLE
        binding.circularProgressIndicator.visibility = View.VISIBLE
        categoriesFetcher.fetchData(ApiUrls.URL_CATEGORY_LIST) { categoriesResponse ->
            categoriesResponse?.let{
                updateCategory(it)
            }
        }
    }

    private fun updateCategory(categoryResponse: DrinksResponse){
        activity?.runOnUiThread {
            adapter = CategoryAdapter(categoryResponse) { categoryName ->
                Log.d("CARD", "Category $categoryName clicked")
                listener.onSelected(categoryName, FragmentType.CATEGORY)
            }
            binding.recyclerViewCategory.adapter = adapter
            binding.recyclerViewCategory.layoutManager = LinearLayoutManager(context)
            binding.circularProgressIndicator.visibility = View.GONE
            val isCategoryListNotEmpty = categoryResponse.drinks?.isNotEmpty() ?: false
            binding.recyclerViewCategory.visibility =
                if (isCategoryListNotEmpty) View.VISIBLE else View.INVISIBLE


        }
    }

    companion object {
        /**
         * @return A new instance of fragment CategoriesFragment.
         */
        @JvmStatic
        fun newInstance() = CategoriesFragment()
    }
}