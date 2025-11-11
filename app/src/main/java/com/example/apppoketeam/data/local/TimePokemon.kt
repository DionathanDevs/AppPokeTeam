package com.example.apppoketeam.data.local
import androidx.room.Entity; import androidx.room.PrimaryKey
@Entity(tableName = "times_pokemon")
data class TimePokemon(@PrimaryKey(autoGenerate = true) val id: Int = 0, val nomeTime: String, val pokemon1: String?, val pokemon2: String?, val pokemon3: String?, val pokemon4: String?, val pokemon5: String?, val pokemon6: String?)