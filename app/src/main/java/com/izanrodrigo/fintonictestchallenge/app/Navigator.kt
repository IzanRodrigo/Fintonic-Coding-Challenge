package com.izanrodrigo.fintonictestchallenge.app

import com.izanrodrigo.fintonictestchallenge.data.Superhero

/**
 * Created by Izan on 2019-10-03.
 */

interface Navigator {
    fun goToSuperheroDetail(superhero: Superhero)
}