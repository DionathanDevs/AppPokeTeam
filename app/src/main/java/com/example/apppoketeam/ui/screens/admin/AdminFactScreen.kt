package com.example.apppoketeam.ui.screens.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apppoketeam.ui.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminFactScreen(factory: ViewModelFactory) {
    val viewModel: AdminFactViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Column(Modifier.padding(16.dp)) {
        Text("Gerenciar Fatos Curiosos (CRUD 2)", style = MaterialTheme.typography.headlineSmall)
        TextField(value = uiState.pokemonName, onValueChange = viewModel::onPokemonNameChange, label = { Text("Nome do Pokémon (ex: pikachu)") }, modifier = Modifier.fillMaxWidth())
        TextField(value = uiState.factText, onValueChange = viewModel::onFactTextChange, label = { Text("Fato curioso") }, modifier = Modifier.fillMaxWidth())
        Button(onClick = viewModel::onSave, modifier = Modifier.fillMaxWidth()) { Text(if (uiState.factEmEdicao == null) "Adicionar Fato" else "Atualizar Fato") }
        LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
            items(uiState.facts) { fact ->
                Row(Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "${fact.pokemonName}: ${fact.fact}", modifier = Modifier.weight(1f))
                    Button(onClick = { viewModel.onEdit(fact) }) { Text("Editar") }
                    Button(onClick = { viewModel.onDelete(fact) }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)) { Text("X") }
                }
            }
        }
    }
}