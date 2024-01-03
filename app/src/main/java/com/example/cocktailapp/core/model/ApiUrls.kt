package com.example.cocktailapp.core.model

enum class ApiUrls(val value: String){
    URL_CATEGORY_LIST("https://www.thecocktaildb.com/api/json/v1/1/list.php?c=list"),
    URL_INGREDIENT_LIST("https://www.thecocktaildb.com/api/json/v1/1/list.php?i=list"),
    URL_COCKTAIL_SEARCH("https://www.thecocktaildb.com/api/json/v1/1/search.php?s="),
    URL_COCKTAIL_FILTER_INGREDIENT("https://www.thecocktaildb.com/api/json/v1/1/filter.php?i="),
    URL_COCKTAIL_FILTER_CATEGORY("https://www.thecocktaildb.com/api/json/v1/1/filter.php?c="),
    URL_COCKTAIL_DETAIL("https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i="),
    URL_COCKTAIL_RANDOM("https://www.thecocktaildb.com/api/json/v1/1/random.php"),
}