package com.izanrodrigo.fintonictestchallenge.ui.list

import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.izanrodrigo.fintonictestchallenge.R
import com.izanrodrigo.fintonictestchallenge.util.extensions.RecyclerAdapter
import com.izanrodrigo.fintonictestchallenge.util.extensions.RecyclerHolder
import kotlinx.android.synthetic.main.fragment_superheroes_list.*
import kotlinx.android.synthetic.main.list_item_superhero.*
import org.koin.android.ext.android.inject

/**
 * Created by Izan on 2019-10-02.
 */

private const val ARG_DATA = "ARG_DATA"

class SuperheroesListFragment : Fragment(), SuperheroesListView {
    private val presenter by inject<SuperheroesListPresenter>()

    private var data: ArrayList<SuperheroesListViewModel>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_superheroes_list, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        presenter.attachView(this)

        val data = savedInstanceState?.getParcelableArrayList<SuperheroesListViewModel>(ARG_DATA)
        if (data == null) {
            presenter.viewDidLoad()
        } else {
            showItems(data)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        data?.let { outState.putParcelableArrayList(ARG_DATA, it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun showLoading() {
        // TODO: Handle execution context changes in the presenter.
        requireActivity().runOnUiThread {
            println("[SuperheroesList] Loading...")
            progressBar.visibility = View.VISIBLE
        }
    }

    override fun showItems(list: List<SuperheroesListViewModel>) {
        data = ArrayList(list)

        // TODO: Handle execution context changes in the presenter.
        requireActivity().runOnUiThread {
            println("[SuperheroesList] Items loaded: $list")
            progressBar.visibility = View.GONE
            recyclerView.adapter = SuperheroesAdapter(list, presenter::itemClicked)
        }
    }

    override fun showError() {
        // TODO: Handle execution context changes in the presenter.
        requireActivity().runOnUiThread {
            println("[SuperheroesList] Error")
            progressBar.visibility = View.GONE
        }
    }
}

private class SuperheroesAdapter(
    val list: List<SuperheroesListViewModel>,
    val onClick: (SuperheroesListViewModel) -> Unit
) : RecyclerAdapter<RecyclerHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_superhero, parent, false)
            .let(::RecyclerHolder)


    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        val item = list[position]
        holder.itemView.setOnClickListener { onClick(item) }
        holder.superheroName.text = item.name
        // TODO: Load image.
    }
}
