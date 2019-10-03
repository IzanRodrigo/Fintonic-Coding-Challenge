package com.izanrodrigo.fintonictestchallenge.data

import android.content.res.Resources
import kotlin.concurrent.thread

/**
 * Created by Izan on 2019-10-02.
 */

class SuperheroesRepository {
    private val testPhotoUrls = arrayOf(
        "https://www.logomaker.com/wp-content/uploads/2018/09/Captain-America-logo.jpg",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTmuZ6Zdon1aCYC8Y9-Rbcn2cc38q4-P5hfgpQbgKWdD0JrV7W_-A",
        "http://www.findthatlogo.com/wp-content/gallery/superheros/thumbs/thumbs_batman-logo.gif"
    )

    private val testData = (0..9).map {
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

    fun getSuperheroes(callback: (Result<List<Superhero>>) -> Unit) {
        // TODO: Coroutines? Rx?
        thread {
            Thread.sleep(1500)
            callback(Result.success(testData))
        }
    }

    fun getSuperheroByName(name: String, callback: (Result<Superhero>) -> Unit) {
        // TODO: Coroutines? Rx?
        thread {
            Thread.sleep(1500)
            val item = testData.firstOrNull { it.name == name }
            if (item != null) callback(Result.success(item))
            else callback(Result.failure(Resources.NotFoundException()))
        }
    }
}