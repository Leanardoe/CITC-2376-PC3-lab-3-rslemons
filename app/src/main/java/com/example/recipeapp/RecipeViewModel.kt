package com.example.recipeapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RecipeViewModel : ViewModel() {
    // Backing property for the list of recipes
    private val _recipes = MutableStateFlow(
        listOf(
            Recipe(1, "Spaghetti Bolognese", "A traditional Italian dish with meat sauce."),
            Recipe(2, "Chicken Curry", "Spicy and savory chicken curry with rice."),
            Recipe(3, "Vegetable Stir Fry", "A quick and healthy vegetable medley."),
            Recipe(4, "Beef Tacos", "Crispy tacos filled with seasoned beef.")
        )
    )
    val recipes: StateFlow<List<Recipe>> = _recipes

    // Currently selected recipe ID
    private val _selectedRecipeId = MutableStateFlow<Int?>(null)
    val selectedRecipeId: StateFlow<Int?> = _selectedRecipeId

    fun selectRecipe(id: Int) {
        _selectedRecipeId.value = id
    }

    fun getRecipeById(id: Int): Recipe? {
        return _recipes.value.find { it.id == id }
    }
}
