package com.izanrodrigo.fintonictestchallenge.data

import android.content.res.Resources

/**
 * Created by Izan on 2019-10-02.
 */

class SuperheroesRepository(
    private val apiService: SuperheroApiService,
    private val dao: SuperheroesDao
) {
    /**
     * Returns the list of superheroes.
     * By default, it uses the cached data unless [refresh] is true.
     * If there's nothing cached, the data is fetched from network.
     */
    suspend fun getSuperheroes(refresh: Boolean = false): Result<List<Superhero>> {
        suspend fun fetchAndCacheData() =
            apiService.getSuperheroes()
                .superheroes
                .apply { dao.insertAll(this) }

        return try {
            val list = if (refresh || dao.isEmpty()) {
                fetchAndCacheData()
            } else {
                dao.getAll()
            }.map(::superhero)
            Result.success(list)
        } catch (ex: Throwable) {
            Result.failure(ex)
        }
    }

    suspend fun getSuperheroByName(name: String): Result<SuperheroDetail> {
        return try {
            val superhero = dao.findByName(name)
                ?: throw Resources.NotFoundException("Superhero with name = $name is not in the DB.")
            Result.success(superhero)
        } catch (ex: Throwable) {
            Result.failure(ex)
        }
    }
}

private fun superhero(detail: SuperheroDetail) =
    Superhero(detail.name, detail.realName, detail.photo)