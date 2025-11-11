package com.example.apppoketeam.data.local
import androidx.room.Entity; import androidx.room.PrimaryKey
@Entity(tableName = "fatos_curiosos")
data class FunFact(@PrimaryKey(autoGenerate = true) val id: Int = 0, val pokemonName: String, val fact: String)