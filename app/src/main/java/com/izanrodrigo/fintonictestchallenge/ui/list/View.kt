package com.izanrodrigo.fintonictestchallenge.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.izanrodrigo.fintonictestchallenge.R
import com.izanrodrigo.fintonictestchallenge.app.Navigator
import com.izanrodrigo.fintonictestchallenge.data.Superhero
import com.izanrodrigo.fintonictestchallenge.util.extensions.RecyclerAdapter
import com.izanrodrigo.fintonictestchallenge.util.extensions.RecyclerHolder
import com.izanrodrigo.fintonictestchallenge.util.extensions.context
import com.izanrodrigo.fintonictestchallenge.util.extensions.transitionPair
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.fragment_superheroes_list.*
import kotlinx.android.synthetic.main.fragment_superheroes_list.errorLayout
import kotlinx.android.synthetic.main.list_item_superhero.*
import kotlinx.android.synthetic.main.list_item_superhero.view.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

/**
 * Created by Izan on 2019-10-02.
 */

private const val ARG_DATA = "ARG_DATA"

class SuperheroesListFragment : Fragment(), SuperheroesListView, Navigator {
    private val presenter by inject<SuperheroesListPresenter> {
        parametersOf(this)
    }

    private var data: ArrayList<Superhero>? = null
    private var lastClickedHolder: RecyclerHolder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        data = savedInstanceState?.getParcelableArrayList<Superhero>(ARG_DATA)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_superheroes_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)

        recyclerView.apply {
            postponeEnterTransition()
            viewTreeObserver
                .addOnPreDrawListener {
                    startPostponedEnterTransition()
                    true
                }
            layoutManager = LinearLayoutManager(requireContext())
        }

        refreshLayout.setColorSchemeResources(R.color.colorAccent)
        refreshLayout.setOnRefreshListener {
            presenter.refreshStarted()
        }

        errorText.setText(R.string.superheroes_list_error_load)
        retryButton.setOnClickListener {
            presenter.retryClicked()
        }

        val data = data
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

    override fun onDestroyView() {
        lastClickedHolder = null
        presenter.detachView()
        super.onDestroyView()
    }

    override fun showLoading() {
        errorLayout.visibility = View.GONE

        // Display global progress bar only the first time.
        if (data.isNullOrEmpty()) {
            progressBar.visibility = View.VISIBLE
        } else {
            refreshLayout.isRefreshing = true
        }
    }

    override fun showItems(list: List<Superhero>) {
        data = ArrayList(list)

        progressBar.visibility = View.GONE
        errorLayout.visibility = View.GONE
        refreshLayout.isRefreshing = false

        recyclerView.adapter = SuperheroesAdapter(list) { it, holder ->
            lastClickedHolder = holder
            presenter.itemClicked(it)
        }
    }

    override fun showError() {
        progressBar.visibility = View.GONE
        refreshLayout.isRefreshing = false

        if (data.isNullOrEmpty()) {
            errorLayout.visibility = View.VISIBLE
        } else {
            Snackbar.make(content, R.string.superheroes_list_error_refresh, Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    override fun goToSuperheroDetail(superhero: Superhero) {
        val holder = lastClickedHolder ?: return
        val directions = SuperheroesListFragmentDirections
            .goToSuperheroDetail(
                superhero,
                holder.superheroName.transitionName,
                holder.superheroRealName.transitionName,
                holder.superheroPhoto.transitionName
            )
        val extras = FragmentNavigatorExtras(
            holder.superheroName.transitionPair,
            holder.superheroRealName.transitionPair,
            holder.superheroPhoto.transitionPair
        )
        findNavController().navigate(directions, extras)
    }
}

typealias AdapterListener = (Superhero, RecyclerHolder) -> Unit

private class SuperheroesAdapter(
    val list: List<Superhero>,
    val onClick: AdapterListener
) : RecyclerAdapter<RecyclerHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_superhero, parent, false)
            .let(::RecyclerHolder)


    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        val item = list[position]
        holder.itemView.setOnClickListener {
            onClick(item, holder)
        }

        holder.superheroName.transitionName =
            holder.context.getString(R.string.transition_name_superhero_name, position)
        holder.superheroRealName.transitionName =
            holder.context.getString(R.string.transition_name_superhero_real_name, position)
        holder.superheroPhoto.transitionName =
            holder.context.getString(R.string.transition_name_superhero_photo, position)

        holder.superheroName.text = item.name
        holder.superheroRealName.text = item.realName

        Glide.with(holder.context)
            .load(item.photo)
            .centerCrop()
            .apply(RequestOptions.circleCropTransform())
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.superheroPhoto)
    }

    override fun onViewRecycled(holder: RecyclerHolder) {
        super.onViewRecycled(holder)
        Glide.with(holder.context)
            .clear(holder.itemView.superheroPhoto)
    }
}
