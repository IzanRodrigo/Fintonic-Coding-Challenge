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
            val list = apiService.getSuperheroes()
                .superheroes
                .map(::superhero)
            Result.success(list)
        } catch (ex: Throwable) {
            Result.failure(ex)
        }
    }

    suspend fun getSuperheroByName(name: String): Result<SuperheroDetail> {
        // FIXME: This is temporary, use cached data instead.
        return try {
            val list = apiService.getSuperheroes()
                .superheroes
                .first { it.name == name }
            Result.success(list)
        } catch (ex: Throwable) {
            Result.failure(ex)
        }
    }
}

private fun superhero(detail: SuperheroDetail) =
    Superhero(detail.name, detail.realName, detail.photo)