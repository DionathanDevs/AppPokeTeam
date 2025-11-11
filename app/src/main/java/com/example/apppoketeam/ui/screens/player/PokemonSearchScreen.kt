package com.example.apppoketeam.ui.screens.player

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.apppoketeam.ui.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonSearchScreen(factory: ViewModelFactory) {
    val viewModel: PokemonSearchViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(Modifier.padding(16.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(value = uiState.query, onValueChange = viewModel::onQueryChange, label = { Text("Nome ou ID do Pokémon") }, modifier = Modifier.fillMaxWidth())
        Button(onClick = viewModel::buscarPokemon) { Text("Buscar") }

        Spacer(Modifier.height(16.dp))

        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else if (uiState.errorMessage != null) {
            // Requisito de Tratamento de Exceção: Mostra o erro da API.
            Text(uiState.errorMessage!!, color = MaterialTheme.colorScheme.error)
        } else if (uiState.resultado != null) {
            // --- CÓDIGO ATUALIZADO PARA MOSTRAR A IMAGEM ---
            Card {
                Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    AsyncImage(
                        model = uiState.resultado.sprites.frontDefault,
                        contentDescription = uiState.resultado.name,
                        modifier = Modifier.size(120.dp),
                        contentScale = ContentScale.Fit
                    )
                    Text(uiState.resultado.name.replaceFirstChar { it.titlecase() })
                }
            }
            // --- FIM DA ATUALIZAÇÃO ---
        }
    }
}