package com.example.apppoketeam.ui.screens.player

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apppoketeam.ui.ViewModelFactory

@Composable
fun PokemonDetailScreen(pokemonName: String, factory: ViewModelFactory) {
    val viewModel: PokemonDetailViewModel = viewModel(factory = factory)
    LaunchedEffect(pokemonName) { viewModel.loadFacts(pokemonName) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(Modifier.padding(16.dp)) {
        Text(pokemonName.replaceFirstChar { it.titlecase() }, style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(16.dp))
        Text("Fatos Curiosos (CRUD do Admin):", style = MaterialTheme.typography.titleMedium)
        if (uiState.facts.isEmpty()) { Text("Nenhum fato curioso cadastrado para este Pokémon.", modifier = Modifier.padding(top = 8.dp)) }
        else {
            LazyColumn(modifier = Modifier.padding(top = 8.dp)) {
                items(uiState.facts) { fact ->
                    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) { Text(fact.fact, modifier = Modifier.padding(16.dp)) }
                }
            }
        }
    }
}