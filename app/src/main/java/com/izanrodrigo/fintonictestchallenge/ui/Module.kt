package com.izanrodrigo.fintonictestchallenge.ui

import com.izanrodrigo.fintonictestchallenge.app.Navigator
import com.izanrodrigo.fintonictestchallenge.ui.detail.SuperheroDetailPresenter
import com.izanrodrigo.fintonictestchallenge.ui.list.SuperheroesListPresenter
import org.koin.dsl.module

/**
 * Created by Izan on 2019-10-02.
 */

val uiModule = module {
    factory { (navigator: Navigator) ->
        SuperheroesListPresenter(get(), navigator)
    }
    factory { (superheroName: String) ->
        SuperheroDetailPresenter(superheroName, get())
    }
}
