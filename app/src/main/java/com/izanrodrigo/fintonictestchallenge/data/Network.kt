package com.izanrodrigo.fintonictestchallenge.data

import retrofit2.http.GET

/**
 * Created by Izan on 2019-10-03.
 */

data class SuperheroesListResponse(
    val superheroes: List<SuperheroDetail>
)

interface SuperheroApiService {
    @GET("bvyob")
    suspend fun getSuperheroes(): SuperheroesListResponse
}