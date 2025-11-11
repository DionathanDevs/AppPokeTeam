package com.example.apppoketeam.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apppoketeam.data.local.AppDatabase
import com.example.apppoketeam.data.remote.RetrofitClient
import com.example.apppoketeam.data.repository.AppRepository
import com.example.apppoketeam.ui.screens.admin.AdminFactViewModel
import com.example.apppoketeam.ui.screens.admin.AdminUserViewModel
import com.example.apppoketeam.ui.screens.login.LoginViewModel
import com.example.apppoketeam.ui.screens.player.PokedexViewModel
import com.example.apppoketeam.ui.screens.player.PlayerTeamBuilderViewModel
import com.example.apppoketeam.ui.screens.player.PokemonDetailViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    private val database by lazy { AppDatabase.getDatabase(context) }
    private val apiService by lazy { RetrofitClient.instance }
    private val repository by lazy { AppRepository(database, apiService) }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(repository) as T
            modelClass.isAssignableFrom(PokedexViewModel::class.java) -> PokedexViewModel(repository) as T
            modelClass.isAssignableFrom(PokemonDetailViewModel::class.java) -> PokemonDetailViewModel(repository) as T
            modelClass.isAssignableFrom(AdminUserViewModel::class.java) -> AdminUserViewModel(repository) as T
            modelClass.isAssignableFrom(AdminFactViewModel::class.java) -> AdminFactViewModel(repository) as T
            modelClass.isAssignableFrom(PlayerTeamBuilderViewModel::class.java) -> PlayerTeamBuilderViewModel(repository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}