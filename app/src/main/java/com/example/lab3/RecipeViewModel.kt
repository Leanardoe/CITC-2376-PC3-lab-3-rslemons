package com.example.lab3

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RecipeViewModel : ViewModel() {

    private val _recipes = MutableStateFlow(
        listOf(
            Recipe(1, "Spaghetti Bolognese", "A classic Italian dish with rich meat sauce."),
            Recipe(2, "Chicken Curry", "A spicy and savory curry with tender chicken pieces."),
            Recipe(3, "Beef Stroganoff", "A creamy dish with sautéed pieces of beef and mushrooms.")
        )
    )
    val recipes: StateFlow<List<Recipe>> = _recipes

    private val _selectedRecipeId = MutableStateFlow<Int?>(null)
    val selectedRecipeId: StateFlow<Int?> = _selectedRecipeId

    fun selectRecipe(id: Int) {
        _selectedRecipeId.value = id
    }

    fun getRecipeById(id: Int): Recipe? = _recipes.value.find { it.id == id }
}
