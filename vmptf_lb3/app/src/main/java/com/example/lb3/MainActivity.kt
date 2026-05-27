package com.example.lb3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EshopAppMain()
        }
    }
}

@Composable
fun EshopAppMain() {
    val navController = rememberNavController()
    val viewModel: AppViewModel = viewModel()

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Каталог") },
                    label = { Text("Каталог") },
                    selected = currentRoute == "catalog",
                    onClick = { navController.navigate("catalog") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Favorite, contentDescription = "Бажане") },
                    label = { Text("Бажане") },
                    selected = currentRoute == "wishlist",
                    onClick = { navController.navigate("wishlist") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Кошик") },
                    label = { Text("Кошик (${viewModel.cart.size})") },
                    selected = currentRoute == "cart",
                    onClick = { navController.navigate("cart") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Профіль") },
                    label = { Text("Профіль") },
                    selected = currentRoute == "profile",
                    onClick = { navController.navigate("profile") }
                )
            }
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = "catalog", Modifier.padding(innerPadding)) {
            composable("catalog") { CatalogScreen(viewModel) }
            composable("wishlist") { WishlistScreen(viewModel) }
            composable("cart") { CartScreen(viewModel) }
            composable("profile") { ProfileScreen(viewModel) }
        }
    }
}