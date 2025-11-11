package com.example.apppoketeam.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [User::class, FunFact::class, TimePokemon::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDAO(): UserDAO
    abstract fun funFactDAO(): FunFactDAO
    abstract fun timePokemonDAO(): TimePokemonDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "pokedex_database"
                )
                        .fallbackToDestructiveMigration()
                        .addCallback(object : RoomDatabase.Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                CoroutineScope(Dispatchers.IO).launch {
                                    prePopulateDatabase(getDatabase(context))
                                }
                            }
                        })
                        .build()
                INSTANCE = instance
                instance
            }
        }

        suspend fun prePopulateDatabase(database: AppDatabase) {
            database.userDAO().inserir(User(username = "admin", password = "123", isAdmin = true))
            database.userDAO().inserir(User(username = "player", password = "123", isAdmin = false))
            database.funFactDAO().inserir(FunFact(pokemonName = "pikachu", fact = "Pikachu armazena eletricidade em suas bochechas."))
        }
    }
}