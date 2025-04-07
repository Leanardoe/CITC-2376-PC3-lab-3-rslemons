package com.example.recipeapp

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import com.example.recipeapp.ui.theme.RecipeAPPTheme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue

import androidx.compose.material3.Scaffold

import com.example.recipeapp.Recipe


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipeAPPTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RecipeExplorerApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun RecipeExplorerApp(viewModel: RecipeViewModel = viewModel()) {
    val context = LocalContext.current
    val windowSizeClass = calculateWindowSizeClass(context as ComponentActivity)


    val recipes by viewModel.recipes.collectAsState()
    val selectedRecipeId by viewModel.selectedRecipeId.collectAsState()
    val selectedRecipe = selectedRecipeId?.let { id ->
        viewModel.getRecipeById(id)
    }

    if (windowSizeClass.widthSizeClass >= WindowWidthSizeClass.Medium) {
        // Tablet Layout - split screen
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                RecipeListScreen(
                    recipes = recipes,
                    onRecipeClick = { viewModel.selectRecipe(it) }
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box(modifier = Modifier.weight(1f)) {
                selectedRecipe?.let {
                    RecipeDetailScreen(recipe = it)
                } ?: Text(
                    "Select a recipe",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    } else {
        // Phone Layout - navigation flow
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "recipeList") {
            composable("recipeList") {
                RecipeListScreen(
                    recipes = recipes,
                    onRecipeClick = { recipeId ->
                        navController.navigate("recipeDetail/$recipeId")
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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RecipeAPPTheme {
        Greeting("Android")
    }
}