package com.example.apppoketeam.ui.screens.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apppoketeam.data.local.FunFact
import com.example.apppoketeam.data.repository.AppRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class DetailUiState(val facts: List<FunFact> = emptyList())
class PokemonDetailViewModel(private val repository: AppRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(DetailUiState()); val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()
    fun loadFacts(pokemonName: String) { viewModelScope.launch { repository.getFactsForPokemon(pokemonName).collect { facts -> _uiState.update { it.copy(facts = facts) } } } }
}