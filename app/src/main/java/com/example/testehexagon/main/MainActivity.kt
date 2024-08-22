package com.example.testehexagon.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testehexagon.main.model.AppDatabase
import com.example.testehexagon.main.viewmodels.ItemRepository
import com.example.testehexagon.main.viewmodels.ItemViewModel
import com.example.testehexagon.main.views.InactiveListScreen
import com.example.testehexagon.main.views.ItemEditScreen
import com.example.testehexagon.main.views.ItemInsertScreen
import com.example.testehexagon.main.views.ItemListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = AppDatabase.getDatabase(applicationContext)
        val repository = ItemRepository(db.itemDao())
        val viewModel = ItemViewModel(repository)

        setContent {
            val navController = rememberNavController()
            AppNavGraph(navController, viewModel)
        }
    }
}

@Composable
fun AppNavGraph(navController: NavHostController, viewModel: ItemViewModel) {
    NavHost(navController, startDestination = "main") {
        composable("main") {
            ItemListScreen(viewModel, navController)
        }
        composable("insert") {
            ItemInsertScreen(viewModel, navController)
        }
        composable("edit/{itemId}") { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId")?.toLongOrNull()
            if (itemId != null) {
                ItemEditScreen(viewModel = viewModel, navController, itemId)
            } else {
                navController.popBackStack()
            }
        }
        composable("inactive") {
            InactiveListScreen(viewModel = viewModel, navController = navController)
        }
    }
}