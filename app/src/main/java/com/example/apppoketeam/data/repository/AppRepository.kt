package com.example.apppoketeam.data.repository

import com.example.apppoketeam.data.local.AppDatabase
import com.example.apppoketeam.data.local.FunFact
import com.example.apppoketeam.data.local.TimePokemon
import com.example.apppoketeam.data.local.User
import com.example.apppoketeam.data.remote.PokeApiService
import com.example.apppoketeam.data.remote.PokemonDTO
import com.example.apppoketeam.data.remote.PokemonListResponse
import kotlinx.coroutines.flow.Flow

class AppRepository(
        private val db: AppDatabase,
        private val apiService: PokeApiService
) {
    private val userDAO = db.userDAO()
    private val funFactDAO = db.funFactDAO()
    private val timeDAO = db.timePokemonDAO()

    suspend fun checkLogin(username: String, password: String): Result<User> {
        return try {
            val user = userDAO.buscarPorUsername(username)
            if (user != null && user.password == password) { Result.success(user) }
            else { Result.failure(Exception("Usuário ou senha inválidos.")) }
        } catch (e: Exception) { Result.failure(e) }
    }

    fun getAllUsers(): Flow<List<User>> = userDAO.buscarTodos()
    suspend fun inserirUser(user: User) = userDAO.inserir(user)
    suspend fun atualizarUser(user: User) = userDAO.atualizar(user)
    suspend fun deletarUser(user: User) = userDAO.deletar(user)

    fun getAllFacts(): Flow<List<FunFact>> = funFactDAO.buscarTodos()
    fun getFactsForPokemon(pokemonName: String): Flow<List<FunFact>> = funFactDAO.buscarFatos(pokemonName)
    suspend fun inserirFact(fact: FunFact) = funFactDAO.inserir(fact)
    suspend fun atualizarFact(fact: FunFact) = funFactDAO.atualizar(fact)
    suspend fun deletarFact(fact: FunFact) = funFactDAO.deletar(fact)

    fun getTodosTimes(): Flow<List<TimePokemon>> = timeDAO.buscarTodos()
    suspend fun inserirTime(time: TimePokemon) = timeDAO.inserir(time)
    suspend fun atualizarTime(time: TimePokemon) = timeDAO.atualizar(time)
    suspend fun deletarTime(time: TimePokemon) = timeDAO.deletar(time)

    suspend fun getPokemonList(): Result<PokemonListResponse> {
        return try { Result.success(apiService.getPokemonList(151)) }
        catch (e: Exception) { Result.failure(e) }
    }
}