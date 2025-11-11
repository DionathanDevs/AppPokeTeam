package com.example.apppoketeam.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun PlayerBottomNav(navController: NavController) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        NavigationBarItem(
                icon = { Icon(Icons.Default.Home, contentDescription = null) },
                label = { Text("Pokédex") },
                selected = currentRoute == "pokedex",
                onClick = { navController.navigate("pokedex") { popUpTo(navController.graph.findStartDestination().id) { saveState = true }; launchSingleTop = true } }
        )
        NavigationBarItem(
                icon = { Icon(Icons.Default.Shield, contentDescription = null) },
                label = { Text("Meus Times") },
                selected = currentRoute == "player_teams",
                onClick = { navController.navigate("player_teams") { popUpTo(navController.graph.findStartDestination().id) { saveState = true }; launchSingleTop = true } }
        )
    }
}

@Composable
fun AdminBottomNav(navController: NavController) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        NavigationBarItem(
                icon = { Icon(Icons.Default.Person, contentDescription = null) },
                label = { Text("Usuários") },
                selected = currentRoute == "admin_users",
                onClick = { navController.navigate("admin_users") { popUpTo(navController.graph.findStartDestination().id) { saveState = true }; launchSingleTop = true } }
        )
        NavigationBarItem(
                icon = { Icon(Icons.Default.List, contentDescription = null) },
                label = { Text("Fatos") },
                selected = currentRoute == "admin_facts",
                onClick = { navController.navigate("admin_facts") { popUpTo(navController.graph.findStartDestination().id) { saveState = true }; launchSingleTop = true } }
        )
    }
}