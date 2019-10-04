package com.izanrodrigo.fintonictestchallenge.app

import androidx.navigation.NavController
import com.izanrodrigo.fintonictestchallenge.ui.list.SuperheroesListFragmentDirections

/**
 * Created by Izan on 2019-10-03.
 */

class Navigator(private val navController: NavController) {
    fun goToSuperheroDetail(name: String) {
        val directions = SuperheroesListFragmentDirections.goToSuperheroDetail(name)
        navController.navigate(directions)
    }
}