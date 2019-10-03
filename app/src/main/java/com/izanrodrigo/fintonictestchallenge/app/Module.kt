package com.izanrodrigo.fintonictestchallenge.app

import com.izanrodrigo.fintonictestchallenge.data.SuperheroApiService
import com.izanrodrigo.fintonictestchallenge.data.SuperheroesRepository
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

/**
 * Created by Izan on 2019-10-02.
 */

val appModule = module {
    single {
        SuperheroesRepository(get())
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.myjson.com/bins/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create<SuperheroApiService>()
    }
}