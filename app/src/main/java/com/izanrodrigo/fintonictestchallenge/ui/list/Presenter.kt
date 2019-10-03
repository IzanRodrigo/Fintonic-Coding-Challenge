package com.izanrodrigo.fintonictestchallenge.ui.list

import android.os.Parcelable
import com.izanrodrigo.fintonictestchallenge.data.SuperheroesRepository
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Created by Izan on 2019-10-02.
 */

@Parcelize
data class SuperheroesListViewModel(
    val name: String,
    val photoUrl: String
) : Parcelable

interface SuperheroesListView {
    fun showLoading()
    fun showItems(list: List<SuperheroesListViewModel>)
    fun showError()
}

class SuperheroesListPresenter(
    private val repository: SuperheroesRepository
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
                .mapCatching { list ->
                    list.map {
                        SuperheroesListViewModel(it.name, it.photo)
                    }
                }.onSuccess {
                    view?.showItems(it)
                }.onFailure {
                    view?.showError()
                }
        }
    }

    fun itemClicked(item: SuperheroesListViewModel) {
        // TODO: Navigate to detail screen.
    }
}