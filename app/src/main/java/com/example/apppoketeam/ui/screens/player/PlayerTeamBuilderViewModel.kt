package com.example.apppoketeam.ui.screens.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apppoketeam.data.local.TimePokemon
import com.example.apppoketeam.data.repository.AppRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

// --- UiState ATUALIZADO ---
// Adicionamos os 6 campos de Pokémon
data class PlayerUiState(
    val times: List<TimePokemon> = emptyList(),
    val nomeTime: String = "",
    val pokemon1: String = "",
    val pokemon2: String = "",
    val pokemon3: String = "",
    val pokemon4: String = "",
    val pokemon5: String = "",
    val pokemon6: String = "",
    val timeEmEdicao: TimePokemon? = null
)

class PlayerTeamBuilderViewModel(private val repository: AppRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(PlayerUiState())
    val uiState: StateFlow<PlayerUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getTodosTimes().collect { times ->
                _uiState.update { it.copy(times = times) }
            }
        }
    }

    // --- Funções de onChance ATUALIZADAS ---
    fun onNomeTimeChange(nome: String) { _uiState.update { it.copy(nomeTime = nome) } }
    fun onPokemon1Change(nome: String) { _uiState.update { it.copy(pokemon1 = nome) } }
    fun onPokemon2Change(nome: String) { _uiState.update { it.copy(pokemon2 = nome) } }
    fun onPokemon3Change(nome: String) { _uiState.update { it.copy(pokemon3 = nome) } }
    fun onPokemon4Change(nome: String) { _uiState.update { it.copy(pokemon4 = nome) } }
    fun onPokemon5Change(nome: String) { _uiState.update { it.copy(pokemon5 = nome) } }
    fun onPokemon6Change(nome: String) { _uiState.update { it.copy(pokemon6 = nome) } }

    // --- onEdit ATUALIZADO ---
    // Agora carrega os 6 Pokémon nos TextFields
    fun onEdit(time: TimePokemon) {
        _uiState.update {
            it.copy(
                timeEmEdicao = time,
                nomeTime = time.nomeTime,
                pokemon1 = time.pokemon1 ?: "",
                pokemon2 = time.pokemon2 ?: "",
                pokemon3 = time.pokemon3 ?: "",
                pokemon4 = time.pokemon4 ?: "",
                pokemon5 = time.pokemon5 ?: "",
                pokemon6 = time.pokemon6 ?: ""
            )
        }
    }

    private fun limparCampos() {
        _uiState.update {
            it.copy(
                timeEmEdicao = null,
                nomeTime = "",
                pokemon1 = "",
                pokemon2 = "",
                pokemon3 = "",
                pokemon4 = "",
                pokemon5 = "",
                pokemon6 = ""
            )
        }
    }

    // --- onSave ATUALIZADO ---
    // Agora salva os 6 Pokémon no banco de dados
    fun onSave() {
        val state = _uiState.value
        if (state.nomeTime.isBlank()) return

        viewModelScope.launch {
            val timeParaSalvar = TimePokemon(
                id = state.timeEmEdicao?.id ?: 0,
                nomeTime = state.nomeTime,
                pokemon1 = state.pokemon1.ifBlank { null },
                pokemon2 = state.pokemon2.ifBlank { null },
                pokemon3 = state.pokemon3.ifBlank { null },
                pokemon4 = state.pokemon4.ifBlank { null },
                pokemon5 = state.pokemon5.ifBlank { null },
                pokemon6 = state.pokemon6.ifBlank { null }
            )

            if (state.timeEmEdicao == null) {
                repository.inserirTime(timeParaSalvar)
            } else {
                repository.atualizarTime(timeParaSalvar)
            }
            limparCampos()
        }
    }

    fun onDelete(time: TimePokemon) {
        viewModelScope.launch {
            repository.deletarTime(time)
        }
    }
}