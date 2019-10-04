package com.izanrodrigo.fintonictestchallenge.ui.list

import com.izanrodrigo.fintonictestchallenge.app.Navigator
import com.izanrodrigo.fintonictestchallenge.data.Superhero
import com.izanrodrigo.fintonictestchallenge.data.SuperheroesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Created by Izan on 2019-10-02.
 */

interface SuperheroesListView {
    fun showLoading()
    fun showItems(list: List<Superhero>)
    fun showError()
}

class SuperheroesListPresenter(
    private val repository: SuperheroesRepository,
    private val navigator: Navigator
) : CoroutineScope {
    private val job = Job()

    override val coroutineContext
        get() = job + Dispatchers.Main

    private var view: SuperheroesListView? = null

    fun attachView(view: SuperheroesListView) {
        this.view = view
    }

    fun detachView() {
        job.cancel()
        view = null
    }

    fun viewDidLoad() {
        launch {
            view?.showLoading()

            repository.getSuperheroes()
                .onSuccess {
                    view?.showItems(it)
                }.onFailure {
                    view?.showError()
                }
        }
    }

    fun itemClicked(item: Superhero) {
        navigator.goToSuperheroDetail(item)
    }
}