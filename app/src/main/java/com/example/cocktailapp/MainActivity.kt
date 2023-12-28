package com.example.cocktailapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.cocktailapp.databinding.ActivityMainBinding
import com.example.cocktailapp.ui.categories.CategoriesFragment
import com.example.cocktailapp.ui.categories.CategoryListener
import com.example.cocktailapp.ui.cocktails.CocktailFragment
import com.example.cocktailapp.ui.cocktails.FragmentType
import com.example.cocktailapp.ui.ingredients.IngredientsFragment
import com.example.cocktailapp.ui.search.SearchFragment
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener, CategoryListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var tabLayout: TabLayout
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tabLayout = binding.bottomBar

        toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        actionBar?.setDisplayShowTitleEnabled(false)
        tabLayout.addOnTabSelectedListener(this)
        displayTab(SearchFragment.newInstance(), R.string.search_text)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_app_bar, menu)
        val searchView =
            binding.toolbar.menu.findItem(R.id.search_top_bar_button)?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextChange(qString: String): Boolean {
                executeQuery(qString)
                return true
            }

            override fun onQueryTextSubmit(qString: String): Boolean {
                executeQuery(qString)
                searchView.clearFocus()
                return true
            }
        })

        return true
    }

    private fun executeQuery(query: String) {
        val fragment = supportFragmentManager.findFragmentById(binding.fragmentContainer.id)
        if (fragment is SearchFragment) {
            fragment.search(query)
        } else {
            displayTab(SearchFragment.newInstance(), R.string.search_text)
            supportFragmentManager.executePendingTransactions()
            binding.bottomBar.getTabAt(0)?.select()
            val newFragment =
                supportFragmentManager.findFragmentById(binding.fragmentContainer.id) as SearchFragment
            newFragment.search(query)
        }
    }

    private fun displayTab(tabFragment: Fragment, titleId: Int) {
        binding.toolbar.title = getString(titleId)
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, tabFragment, getString(titleId)).commit()
    }

    private fun onTabChange(tab: TabLayout.Tab) {
        when (tab.position) {
            0 -> displayTab(SearchFragment.newInstance(), R.string.search_text)
            1 -> displayTab(CategoriesFragment.newInstance(), R.string.categories_text)
            2 -> displayTab(IngredientsFragment.newInstance(), R.string.ingredients_text)
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

    override fun onSelected(categoryName: String, fragmentName: FragmentType) {
        val cocktailFragment = CocktailFragment.newInstance(categoryName, fragmentName)

        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, cocktailFragment)
            .addToBackStack(null)
            .commit()
    }

}