package com.example.apppoketeam.ui.screens.player

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apppoketeam.ui.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerTeamsScreen(factory: ViewModelFactory) {
    val viewModel: PlayerTeamBuilderViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Adicionamos verticalScroll para caber tudo na tela
    Column(
        Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Gerenciar Time (CRUD 1)", style = MaterialTheme.typography.headlineSmall)

        // --- FORMULÁRIO ATUALIZADO ---
        TextField(value = uiState.nomeTime, onValueChange = viewModel::onNomeTimeChange, label = { Text("Nome do Time") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        TextField(value = uiState.pokemon1, onValueChange = viewModel::onPokemon1Change, label = { Text("Pokémon 1") }, modifier = Modifier.fillMaxWidth())
        TextField(value = uiState.pokemon2, onValueChange = viewModel::onPokemon2Change, label = { Text("Pokémon 2") }, modifier = Modifier.fillMaxWidth())
        TextField(value = uiState.pokemon3, onValueChange = viewModel::onPokemon3Change, label = { Text("Pokémon 3") }, modifier = Modifier.fillMaxWidth())
        TextField(value = uiState.pokemon4, onValueChange = viewModel::onPokemon4Change, label = { Text("Pokémon 4") }, modifier = Modifier.fillMaxWidth())
        TextField(value = uiState.pokemon5, onValueChange = viewModel::onPokemon5Change, label = { Text("Pokémon 5") }, modifier = Modifier.fillMaxWidth())
        TextField(value = uiState.pokemon6, onValueChange = viewModel::onPokemon6Change, label = { Text("Pokémon 6") }, modifier = Modifier.fillMaxWidth())

        Spacer(Modifier.height(8.dp))
        Button(onClick = viewModel::onSave, modifier = Modifier.fillMaxWidth()) {
            Text(if (uiState.timeEmEdicao == null) "Criar Time" else "Atualizar Time")
        }

        Spacer(Modifier.height(16.dp))
        Text("Meus Times", style = MaterialTheme.typography.headlineSmall)

        // LazyColumn aqui não é o ideal dentro de outra Column rolável,
        // mas para este CRUD simples (requisito de lista), ele funciona.
        LazyColumn(modifier = Modifier.height(300.dp)) { // Damos uma altura fixa
            items(uiState.times) { time ->
                Row(Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text(time.nomeTime, modifier = Modifier.weight(1f))
                    Button(onClick = { viewModel.onEdit(time) }) { Text("Editar") }
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = { viewModel.onDelete(time) }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)) { Text("X") }
                }
            }
        }
    }
}