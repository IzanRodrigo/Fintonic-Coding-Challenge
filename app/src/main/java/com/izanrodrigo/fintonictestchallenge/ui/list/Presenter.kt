package com.izanrodrigo.fintonictestchallenge.ui.list

import android.os.Parcelable
import com.izanrodrigo.fintonictestchallenge.data.SuperheroesRepository
import kotlinx.android.parcel.Parcelize

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
) {
    private var view: SuperheroesListView? = null

    fun attachView(view: SuperheroesListView) {
        this.view = view
    }

    fun detachView() {
        // TODO: Cancel subscriptions / coroutines.
        view = null
    }

    fun viewDidLoad() {
        view?.showLoading()

        // TODO: Handle execution context changes.
        repository.getSuperheroes { result ->
            result.mapCatching { list ->
                list.map {
                    SuperheroesListViewModel(it.name, it.photoUrl)
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
        repository.getSuperheroByName(item.name) { result ->
            println(result)
        }
    }
}