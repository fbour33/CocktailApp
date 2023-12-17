package com.example.cocktailapp.ui.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocktailapp.core.model.CategoriesResponse
import com.example.cocktailapp.core.service.CategoriesFetcher
import com.example.cocktailapp.databinding.FragmentCategoriesBinding

/**
 * A simple [Fragment] subclass.
 * Use the [CategoriesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var adapter: CategoryAdapter
    private val categoriesFetcher: CategoriesFetcher = CategoriesFetcher()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CategoryAdapter(CategoriesResponse())
        binding.recyclerViewCategory.visibility = View.INVISIBLE
        binding.circularProgressIndicator.visibility = View.VISIBLE
        categoriesFetcher.fetchData { categoriesResponse ->
            categoriesResponse?.let {
                updateCategory(it)
            }
        }
    }

    private fun updateCategory(categoryResponse: CategoriesResponse) {
        activity?.runOnUiThread {
            adapter = CategoryAdapter(categoryResponse)
            binding.recyclerViewCategory.adapter = adapter
            binding.recyclerViewCategory.layoutManager = LinearLayoutManager(context)
            binding.circularProgressIndicator.visibility = View.GONE
            val isCategoryListNotEmpty = categoryResponse.categories?.isNotEmpty() ?: false
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