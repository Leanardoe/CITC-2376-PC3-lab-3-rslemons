package com.example.recipeapp

sealed class Screen(val route: String) {
    object RecipeList : Screen("recipeList")
    object RecipeDetail : Screen("recipeDetail/{recipeId}") {
        fun createRoute(recipeId: Int) = "recipeDetail/$recipeId"
    }
}
