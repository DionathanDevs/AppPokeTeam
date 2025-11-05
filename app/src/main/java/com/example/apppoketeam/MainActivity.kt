package com.example.apppoketeam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.apppoketeam.ui.screens.admin.AdminDashboard
import com.example.apppoketeam.ui.screens.login.LoginScreen
import com.example.apppoketeam.ui.screens.player.PlayerDashboardScreen
import com.example.apppoketeam.ui.theme.AppPokeTeamTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppPokeTeamTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AppPrincipal()
                }
            }
        }
    }
}

@Composable
fun AppPrincipal() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController = navController) }
        composable("player_dashboard") { PlayerDashboardScreen() }
        composable("admin_dashboard") { AdminDashboard() }
    }
}

