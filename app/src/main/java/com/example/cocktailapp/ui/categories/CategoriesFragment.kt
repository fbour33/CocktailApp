package com.example.cocktailapp.ui.categories

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailapp.core.model.CategoriesResponse
import com.example.cocktailapp.core.service.CategoriesFetcher
import com.example.cocktailapp.databinding.FragmentCategoriesBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CategoriesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoriesFragment : Fragment() {

    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CategoryAdapter
    private lateinit var recyclerView: RecyclerView
    private val categoriesFetcher: CategoriesFetcher = CategoriesFetcher()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CategoryAdapter(CategoriesResponse())
        binding.recyclerViewCategory.visibility = View.INVISIBLE
        categoriesFetcher.fetchData() { categoriesResponse ->
            categoriesResponse?.let{
                updateCategory(it)
            }
        }
    }

    private fun updateCategory(categoryResponse: CategoriesResponse){
        activity?.runOnUiThread {
            adapter = CategoryAdapter(categoryResponse)
            binding.recyclerViewCategory.adapter = adapter
            binding.recyclerViewCategory.layoutManager = LinearLayoutManager(context)
            val handler = Handler(Looper.getMainLooper())
            val isCategoryListNotEmpty = categoryResponse.categories?.isNotEmpty() ?: false
            binding.recyclerViewCategory.visibility =
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
         * @return A new instance of fragment CategoriesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CategoriesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}