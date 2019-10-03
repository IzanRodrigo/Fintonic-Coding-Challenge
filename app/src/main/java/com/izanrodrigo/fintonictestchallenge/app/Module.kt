package com.izanrodrigo.fintonictestchallenge.app

import com.izanrodrigo.fintonictestchallenge.data.SuperheroesRepository
import org.koin.dsl.module

/**
 * Created by Izan on 2019-10-02.
 */

val appModule = module {
    single {
        SuperheroesRepository()
    }
}