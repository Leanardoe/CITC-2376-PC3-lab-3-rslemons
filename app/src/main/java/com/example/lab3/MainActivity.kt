package com.example.lab3

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.window.*
import com.example.lab3.ui.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab3Theme {
                RecipeExplorerApp()
            }
        }
    }
}

@Composable
fun RecipeExplorerApp(viewModel: RecipeViewModel = viewModel()) {
    val context = LocalContext.current
    val activity = context as Activity
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

    val recipes = viewModel.recipes.collectAsState().value
    val selectedRecipeId = viewModel.selectedRecipeId.collectAsState().value
    val selectedRecipe = selectedRecipeId?.let { viewModel.getRecipeById(it) }

    if (windowSizeClass.widthSizeClass >= WindowWidthSizeClass.MEDIUM) {
        // Tablet Layout - split screen
        Row(Modifier.fillMaxSize().padding(16.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                RecipeListScreen(
                    recipes = recipes,
                    onRecipeClick = { viewModel.selectRecipe(it) }
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box(modifier = Modifier.weight(1f)) {
                if (selectedRecipe != null) {
                    RecipeDetailScreen(recipe = selectedRecipe)
                } else {
                    Text("Select a recipe", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    } else {
        // Phone Layout - navigation
        val navController = rememberNavController()
        NavHost(navController, startDestination = "recipeList") {
            composable("recipeList") {
                RecipeListScreen(
                    recipes = recipes,
                    onRecipeClick = {
                        navController.navigate("recipeDetail/$it")
                    }
                )
            }
            composable(
                route = "recipeDetail/{recipeId}",
                arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
            ) { backStackEntry ->
                val recipeId = backStackEntry.arguments?.getInt("recipeId")
                val recipe = recipeId?.let { viewModel.getRecipeById(it) }
                if (recipe != null) {
                    RecipeDetailScreen(recipe = recipe)
                }
            }
        }
    }
}