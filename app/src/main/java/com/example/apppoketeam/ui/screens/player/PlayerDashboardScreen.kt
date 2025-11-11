package com.example.apppoketeam.ui.screens.player

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.apppoketeam.ui.ViewModelFactory
import com.example.apppoketeam.ui.components.PlayerBottomNav

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerDashboardScreen(rootNavController: NavHostController) {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Área do Jogador") },
                actions = {
                    IconButton(onClick = { rootNavController.navigate("login") { popUpTo(0) } }) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Sair")
                    }
                }
            )
        },
        bottomBar = { PlayerBottomNav(navController = navController) }
    ) { padding ->
        NavHost(navController = navController, startDestination = "pokedex", Modifier.padding(padding)) {
            // Rota para a Pokédex (Busca)
            composable("pokedex") {
                PokedexScreen(navController = navController, factory = ViewModelFactory(LocalContext.current))
            }
            // Rota para o CRUD de Times
            composable("player_teams") {
                PlayerTeamsScreen(factory = ViewModelFactory(LocalContext.current))
            }
            // Rota para os Detalhes do Pokémon
            composable("pokemon_detail/{pokemonName}") { backStackEntry ->
                val name = backStackEntry.arguments?.getString("pokemonName") ?: ""
                PokemonDetailScreen(pokemonName = name, factory = ViewModelFactory(LocalContext.current))
            }
        }
    }
}