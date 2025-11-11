package com.example.apppoketeam.ui.screens.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apppoketeam.data.remote.PokemonNameUrl
import com.example.apppoketeam.data.repository.AppRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

// Esta é a classe de Estado da UI para esta tela
data class PokedexUiState(
    val query: String = "",
    val allPokemon: List<PokemonNameUrl> = emptyList(),
    val filteredPokemon: List<PokemonNameUrl> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

// Este é o ViewModel que gerencia a lógica
class PokedexViewModel(private val repository: AppRepository) : ViewModel() {

    // _uiState é privada e mutável (só o ViewModel mexe)
    private val _uiState = MutableStateFlow(PokedexUiState())
    // uiState é pública e imutável (a Tela apenas lê)
    val uiState: StateFlow<PokedexUiState> = _uiState.asStateFlow()

    init {
        // Carrega a lista de 151 Pokémon da API assim que o ViewModel é criado
        loadPokemonList()
    }

    private fun loadPokemonList() {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        viewModelScope.launch {
            // Chama o Repositório (que usa o Retrofit)
            val result = repository.getPokemonList()
            result.fold(
                onSuccess = { response ->
                    // Se der certo, atualiza o estado com a lista
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            allPokemon = response.results,
                            filteredPokemon = response.results
                        )
                    }
                },
                onFailure = { error ->
                    // Se der errado (try-catch), atualiza o estado com a mensagem de erro
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = "Falha ao carregar Pokédex: ${error.message}")
                    }
                }
            )
        }
    }

    // Esta função é chamada pelo TextField da tela toda vez que o usuário digita
    fun onQueryChange(query: String) {
        _uiState.update { it.copy(query = query) }
        filterPokemon(query)
    }

    // Esta é a LÓGICA DE BUSCA/FILTRO (requisito do trabalho)
    private fun filterPokemon(query: String) {
        val filteredList = if (query.isBlank()) {
            // Se a busca estiver vazia, mostra todos os Pokémon
            _uiState.value.allPokemon
        } else {
            // Se houver texto, filtra a lista 'allPokemon'
            _uiState.value.allPokemon.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
        // Atualiza o estado com a nova lista filtrada
        _uiState.update { it.copy(filteredPokemon = filteredList) }
    }
}