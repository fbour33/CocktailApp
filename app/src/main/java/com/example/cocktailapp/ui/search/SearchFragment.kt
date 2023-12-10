package com.example.cocktailapp.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocktailapp.core.model.ApiUrls
import com.example.cocktailapp.core.model.Drink
import com.example.cocktailapp.core.service.SearchDrinkFetcher
import com.example.cocktailapp.databinding.FragmentSearchBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentSearchBinding
    private val searchService = SearchDrinkFetcher()
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        search("")

        return binding.root
    }
    fun search(query: String){
        Log.i("SEARCH", "We search a new data: $query")
        binding.noResultView.visibility = View.INVISIBLE
        binding.cocktailRecyclerView.visibility = View.INVISIBLE
        binding.circularProgressIndicator.visibility = View.VISIBLE
        searchService.fetchData(ApiUrls.URL_COCKTAIL_SEARCH, query) { drinksResponse ->
            drinksResponse?.let {
                updateUI(it.drinks)
            }
        }
    }

    private fun updateUI(drinks: List<Drink>?) {
        activity?.runOnUiThread {
            val drinkList = drinks?: emptyList()
            searchAdapter = SearchAdapter(drinkList)
            binding.cocktailRecyclerView.adapter = searchAdapter
            binding.cocktailRecyclerView.layoutManager = LinearLayoutManager(context)
            binding.circularProgressIndicator.visibility = View.GONE
            if (drinkList.isEmpty()){
                binding.noResultView.visibility = View.VISIBLE
            }else{
                binding.cocktailRecyclerView.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}