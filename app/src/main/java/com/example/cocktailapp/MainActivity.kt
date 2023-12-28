package com.example.cocktailapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tabLayout = binding.bottomBar
        tabLayout.addOnTabSelectedListener(this)
        displayTab(SearchFragment.newInstance("", ""))
    }

    private fun displayTab(tabFragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainer.id, tabFragment)
            .commit()
    }

    private fun onTabChange(tab: TabLayout.Tab){
        when(tab.position){
            0 -> displayTab(SearchFragment.newInstance("", ""))
            1 -> displayTab(CategoriesFragment.newInstance("", ""))
            2 -> displayTab(IngredientsFragment.newInstance("", ""))
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