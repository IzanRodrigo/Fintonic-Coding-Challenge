package com.izanrodrigo.fintonictestchallenge.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.izanrodrigo.fintonictestchallenge.R
import com.izanrodrigo.fintonictestchallenge.data.SuperheroDetail
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.fragment_superhero_detail.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

/**
 * Created by Izan on 2019-10-03.
 */

private const val ARG_DATA = "ARG_DATA"

class SuperheroDetailFragment : Fragment(), SuperheroDetailView {
    private val args by navArgs<SuperheroDetailFragmentArgs>()

    private inline val superhero
        get() = args.superhero

    private val presenter by inject<SuperheroDetailPresenter> {
        parametersOf(superhero.name)
    }

    private val contentViews by lazy {
        listOf(superheroPowerCard, superheroAbilitiesCard, superheroGroupsCard)
    }

    private var data: SuperheroDetail? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        data = savedInstanceState?.getParcelable(ARG_DATA)
        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_superhero_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)

        superheroName.transitionName = args.superheroNameTransitionName
        superheroRealName.transitionName = args.superheroRealNameTransitionName
        superheroPhoto.transitionName = args.superheroPhotoTransitionName


        errorText.setText(R.string.superhero_detail_error_load)
        retryButton.setOnClickListener {
            presenter.retryClicked()
        }

        Glide.with(this)
            .load(superhero.photo)
            .centerCrop()
            .apply(RequestOptions.circleCropTransform())
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(superheroPhoto)

        superheroName.text = superhero.name
        superheroRealName.text = superhero.realName

        val data = data
        if (data == null) {
            presenter.viewDidLoad()
        } else {
            showItem(data)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireActivity().title = superhero.name
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        data?.let { outState.putParcelable(ARG_DATA, it) }
    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }

    override fun showLoading() {
        superheroErrorCard.visibility = View.GONE
        errorLayout.visibility = View.GONE
        contentViews.forEach { it.visibility = View.GONE }
        progressBar.visibility = View.VISIBLE
    }

    override fun showItem(item: SuperheroDetail) {
        data = item

        progressBar.visibility = View.GONE
        superheroErrorCard.visibility = View.GONE
        errorLayout.visibility = View.GONE
        contentViews.forEach { it.visibility = View.VISIBLE }

        superheroPower.text = item.power
        superheroAbilities.text = item.abilities
        superheroGroups.text = item.groups
    }

    override fun showError() {
        contentViews.forEach { it.visibility = View.GONE }
        progressBar.visibility = View.GONE
        superheroErrorCard.visibility = View.VISIBLE
        errorLayout.visibility = View.VISIBLE
    }
}