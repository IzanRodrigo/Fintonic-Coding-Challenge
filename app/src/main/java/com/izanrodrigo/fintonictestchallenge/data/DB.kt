package com.izanrodrigo.fintonictestchallenge.data

import androidx.room.*

/**
 * Created by Izan on 2019-10-05.
 */

@Database(entities = [SuperheroDetail::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "db.sqlite"
        const val TABLE_NAME_SUPERHEROES = "superheroes"
    }

    abstract fun superheroesDao(): SuperheroesDao
}

@Dao
interface SuperheroesDao {
    @Query("SELECT * FROM superheroes")
    suspend fun getAll(): List<SuperheroDetail>

    @Query("SELECT * FROM superheroes WHERE :name LIKE name LIMIT 1")
    suspend fun findByName(name: String): SuperheroDetail?

    @Query("SELECT COUNT(name) FROM superheroes")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<SuperheroDetail>)
}

suspend inline fun SuperheroesDao.isEmpty() = count() == 0