package com.izanrodrigo.fintonictestchallenge.data

import android.content.res.Resources
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

/**
 * Created by Izan on 2019-10-02.
 */

class SuperheroesRepository {
    private val testPhotoUrls = arrayOf(
        "https://www.logomaker.com/wp-content/uploads/2018/09/Captain-America-logo.jpg",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTmuZ6Zdon1aCYC8Y9-Rbcn2cc38q4-P5hfgpQbgKWdD0JrV7W_-A",
        "https://www.logomaker.com/wp-content/uploads/2018/09/Green-Lantern-logo.png"
    )

    private val testData = (0..90).map {
        val url = testPhotoUrls[it % testPhotoUrls.size]
        Superhero(
            "Superhero $it",
            url,
            "Superhero $it",
            "1.8m",
            "Power $it",
            "Abilities $it",
            "Groups $it"
        )
    }

    fun getSuperheroesAsync() = GlobalScope.async(Dispatchers.IO) {
        delay(1500)
        Result.success(testData)
    }

    fun getSuperheroByNameAsync(name: String) = GlobalScope.async(Dispatchers.IO) {
        delay(1500)
        val item = testData.firstOrNull { it.name == name }
        when {
            item != null -> Result.success(item)
            else -> Result.failure(Resources.NotFoundException())
        }
    }
}