package com.izanrodrigo.fintonictestchallenge.ui.detail

import com.izanrodrigo.fintonictestchallenge.data.SuperheroDetail
import com.izanrodrigo.fintonictestchallenge.data.SuperheroesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Created by Izan on 2019-10-03.
 */

interface SuperheroDetailView {
    fun showLoading()
    fun showItem(item: SuperheroDetail)
    fun showError()
}

class SuperheroDetailPresenter(
    private val superheroName: String,
    private val repository: SuperheroesRepository
) : CoroutineScope {
    private val job = Job()

    override val coroutineContext
        get() = job + Dispatchers.Main

    private var view: SuperheroDetailView? = null

    fun attachView(view: SuperheroDetailView) {
        this.view = view
    }

    fun detachView() {
        job.cancel()
        view = null
    }

    fun viewDidLoad() {
        launch {
            view?.showLoading()

            repository.getSuperheroByName(superheroName)
                .onSuccess {
                    view?.showItem(it)
                }.onFailure {
                    view?.showError()
                }
        }
    }
}