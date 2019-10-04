package com.izanrodrigo.fintonictestchallenge.data

/**
 * Created by Izan on 2019-10-02.
 */

class SuperheroesRepository(
    private val apiService: SuperheroApiService
) {
    suspend fun getSuperheroes(): Result<List<Superhero>> {
        // TODO: Cache data?
        return try {
            val list = apiService.getSuperheroes().superheroes
            Result.success(list)
        } catch (ex: Throwable) {
            Result.failure(ex)
        }
    }

    suspend fun getSuperheroByName(name: String): Result<Superhero> {
        // FIXME: This is temporary, use cached data instead.
        return getSuperheroes().mapCatching { it.first { it.name == name } }
    }
}