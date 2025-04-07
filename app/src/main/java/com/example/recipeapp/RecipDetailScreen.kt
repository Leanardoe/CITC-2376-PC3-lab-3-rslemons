// RecipeDetailScreen.kt
package com.example.recipeapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RecipeDetailScreen(recipe: Recipe) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(recipe.title, style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text(recipe.description, style = MaterialTheme.typography.bodyMedium)
    }
}
