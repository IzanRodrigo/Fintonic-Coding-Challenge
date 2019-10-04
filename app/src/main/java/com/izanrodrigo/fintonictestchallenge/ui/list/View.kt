package com.izanrodrigo.fintonictestchallenge.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.izanrodrigo.fintonictestchallenge.R
import com.izanrodrigo.fintonictestchallenge.util.extensions.RecyclerAdapter
import com.izanrodrigo.fintonictestchallenge.util.extensions.RecyclerHolder
import com.izanrodrigo.fintonictestchallenge.util.extensions.context
import kotlinx.android.synthetic.main.fragment_superheroes_list.*
import kotlinx.android.synthetic.main.list_item_superhero.*
import kotlinx.android.synthetic.main.list_item_superhero.view.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

/**
 * Created by Izan on 2019-10-02.
 */

private const val ARG_DATA = "ARG_DATA"

class SuperheroesListFragment : Fragment(), SuperheroesListView {
    private val presenter by inject<SuperheroesListPresenter> {
        parametersOf(findNavController())
    }

    private var data: ArrayList<SuperheroesListViewModel>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_superheroes_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        presenter.attachView(this)

        val data = savedInstanceState?.getParcelableArrayList<SuperheroesListViewModel>(ARG_DATA)
        if (data == null) {
            presenter.viewDidLoad()
        } else {
            showItems(data)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireActivity().setTitle(R.string.fragment_superheroes_list_title)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        data?.let { outState.putParcelableArrayList(ARG_DATA, it) }
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun showLoading() {
        println("[SuperheroesList] Loading...")
        progressBar.visibility = View.VISIBLE
    }

    override fun showItems(list: List<SuperheroesListViewModel>) {
        data = ArrayList(list)

        println("[SuperheroesList] Items loaded: $list")
        progressBar.visibility = View.GONE
        recyclerView.adapter = SuperheroesAdapter(list) {
            presenter.itemClicked(it)
        }
    }

    override fun showError() {
        println("[SuperheroesList] Error")
        progressBar.visibility = View.GONE
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

        Glide.with(holder.context)
            .load(item.photoUrl)
            .centerCrop()
            .apply(RequestOptions.circleCropTransform())
            .into(holder.superheroPhoto)
    }

    override fun onViewRecycled(holder: RecyclerHolder) {
        super.onViewRecycled(holder)
        Glide.with(holder.context)
            .clear(holder.itemView.superheroPhoto)
    }
}
