package com.example.apppoketeam.ui.screens.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apppoketeam.data.local.FunFact
import com.example.apppoketeam.data.repository.AppRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class AdminFactUiState(val facts: List<FunFact> = emptyList(), val pokemonName: String = "", val factText: String = "", val factEmEdicao: FunFact? = null)
class AdminFactViewModel(private val repository: AppRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(AdminFactUiState()); val uiState: StateFlow<AdminFactUiState> = _uiState.asStateFlow()
    init { viewModelScope.launch { repository.getAllFacts().collect { facts -> _uiState.update { it.copy(facts = facts) } } } }
    fun onPokemonNameChange(name: String) { _uiState.update { it.copy(pokemonName = name) } }
    fun onFactTextChange(text: String) { _uiState.update { it.copy(factText = text) } }
    fun onEdit(fact: FunFact) { _uiState.update { it.copy(factEmEdicao = fact, pokemonName = fact.pokemonName, factText = fact.fact) } }
    fun onSave() { val s = _uiState.value; if (s.pokemonName.isBlank() || s.factText.isBlank()) return; viewModelScope.launch { if (s.factEmEdicao == null) { repository.inserirFact(FunFact(pokemonName = s.pokemonName.lowercase(), fact = s.factText)) } else { repository.atualizarFact(s.factEmEdicao.copy(pokemonName = s.pokemonName.lowercase(), fact = s.factText)) }; _uiState.update { it.copy(factEmEdicao = null, pokemonName = "", factText = "") } } }
    fun onDelete(fact: FunFact) { viewModelScope.launch { repository.deletarFact(fact) } }
}