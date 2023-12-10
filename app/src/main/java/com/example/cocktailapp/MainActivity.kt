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
import com.example.cocktailapp.ui.ingredients.IngredientsFragment
import com.example.cocktailapp.ui.search.SearchFragment
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener {

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
        displayTab(SearchFragment.newInstance("", ""), "Search")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_app_bar, menu)
        binding.toolbar.title = "Search" // Avoid to have CocktailApp
        val searchView = binding.toolbar.menu.findItem(R.id.search_top_bar_button)?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextChange(qString: String): Boolean {
                val fragment = supportFragmentManager.findFragmentById(binding.fragmentContainer.id) as SearchFragment
                fragment.search(qString)
                return true
            }
            override fun onQueryTextSubmit(qString: String): Boolean {
                searchView.clearFocus()
                return true
            }
        })

        return true
    }

    private fun displayTab(tabFragment: Fragment, headerTitle: String){
        binding.toolbar.title = headerTitle
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainer.id, tabFragment, headerTitle)
            .commit()
    }

    private fun onTabChange(tab: TabLayout.Tab){
        when(tab.position){
            0 -> displayTab(SearchFragment.newInstance("", ""), "Search")
            1 -> displayTab(CategoriesFragment.newInstance("", ""), "Categories")
            2 -> displayTab(IngredientsFragment.newInstance("", ""), "Ingredients")
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


}