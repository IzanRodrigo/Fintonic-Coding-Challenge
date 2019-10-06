package com.izanrodrigo.fintonictestchallenge.app

import androidx.room.Room
import com.izanrodrigo.fintonictestchallenge.data.AppDatabase
import com.izanrodrigo.fintonictestchallenge.data.SuperheroApiService
import com.izanrodrigo.fintonictestchallenge.data.SuperheroesRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

/**
 * Created by Izan on 2019-10-02.
 */

val appModule = module {
    single {
        SuperheroesRepository(get(), get())
    }

    single {
        Retrofit.Builder()
            .baseUrl(SuperheroApiService.ENDPOINT)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create<SuperheroApiService>()
    }

    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            AppDatabase.DB_NAME
        ).build()
    }

    single {
        get<AppDatabase>().superheroesDao()
    }
}