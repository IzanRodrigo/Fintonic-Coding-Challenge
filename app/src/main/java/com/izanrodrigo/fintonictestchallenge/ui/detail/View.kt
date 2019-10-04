package com.izanrodrigo.fintonictestchallenge.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.izanrodrigo.fintonictestchallenge.R
import com.izanrodrigo.fintonictestchallenge.data.Superhero
import kotlinx.android.synthetic.main.fragment_superhero_detail.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

/**
 * Created by Izan on 2019-10-03.
 */

private const val ARG_DATA = "ARG_DATA"

class SuperheroDetailFragment : Fragment(), SuperheroDetailView {
    private val args by navArgs<SuperheroDetailFragmentArgs>()

    private val presenter by inject<SuperheroDetailPresenter> {
        parametersOf(args.superheroName)
    }
    private var data: Superhero? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_superhero_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.attachView(this)

        val data = savedInstanceState?.getParcelable<Superhero>(ARG_DATA)
        if (data == null) {
            presenter.viewDidLoad()
        } else {
            showItem(data)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireActivity().title = args.superheroName
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        data?.let { outState.putParcelable(ARG_DATA, it) }
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun showLoading() {
        println("[SuperheroDetail] Loading...")
        contentView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    override fun showItem(item: Superhero) {
        data = item

        println("[SuperheroDetail] Item loaded: $item")
        progressBar.visibility = View.GONE
        contentView.visibility = View.VISIBLE

        Glide.with(this)
            .load(item.photo)
            .centerCrop()
            .apply(RequestOptions.circleCropTransform())
            .into(superheroPhoto)

        superheroName.text = item.name
        superheroRealName.text = item.realName
        superheroPower.text = item.power
        superheroAbilities.text = item.abilities
        superheroGroups.text = item.groups
    }

    override fun showError() {
        println("[SuperheroDetail] Error")
        contentView.visibility = View.GONE
        progressBar.visibility = View.GONE
        // TODO: Show error view.
    }
}