package com.example.apppoketeam.data.remote
import com.google.gson.annotations.SerializedName; import retrofit2.Retrofit; import retrofit2.converter.gson.GsonConverterFactory; import retrofit2.http.GET; import retrofit2.http.Path; import retrofit2.http.Query
data class PokemonListResponse(val results: List<PokemonNameUrl>)
data class PokemonNameUrl(val name: String, val url: String)
data class PokemonDTO(val id: Int, val name: String, val sprites: PokemonSprites, val types: List<PokemonTypeSlot>)
data class PokemonSprites(@SerializedName("front_default") val frontDefault: String?)
data class PokemonTypeSlot(val type: PokemonType)
data class PokemonType(val name: String)
interface PokeApiService { @GET("pokemon") suspend fun getPokemonList(@Query("limit") limit: Int = 151): PokemonListResponse; @GET("pokemon/{nameOrId}") suspend fun getPokemonDetails(@Path("nameOrId") nameOrId: String): PokemonDTO }
object RetrofitClient { val instance: PokeApiService by lazy { Retrofit.Builder().baseUrl("https://pokeapi.co/api/v2/").addConverterFactory(GsonConverterFactory.create()).build().create(PokeApiService::class.java) } }