package com.izanrodrigo.fintonictestchallenge.ui

import androidx.navigation.NavController
import com.izanrodrigo.fintonictestchallenge.app.Navigator
import com.izanrodrigo.fintonictestchallenge.ui.detail.SuperheroDetailPresenter
import com.izanrodrigo.fintonictestchallenge.ui.list.SuperheroesListPresenter
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

/**
 * Created by Izan on 2019-10-02.
 */

val uiModule = module {
    factory { (navController: NavController) -> Navigator(navController) }
    factory { (navController: NavController) ->
        val navigator = get<Navigator> { parametersOf(navController) }
        SuperheroesListPresenter(get(), navigator)
    }
    factory { (superheroName: String) ->
        SuperheroDetailPresenter(superheroName, get())
    }
}
