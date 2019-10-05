package com.izanrodrigo.fintonictestchallenge.util.extensions

import android.content.Context
import android.view.View
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

/**
 * Created by Izan on 2019-10-02.
 */

inline val RecyclerView.Adapter<*>.isEmpty: Boolean
    get() = itemCount == 0

inline val RecyclerView.ViewHolder.context: Context
    get() = itemView.context

open class RecyclerHolder(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    @CallSuper
    open fun onViewRecycled() = Unit

}

abstract class RecyclerAdapter<VH : RecyclerHolder> : RecyclerView.Adapter<VH>() {

    @CallSuper
    override fun onViewRecycled(holder: VH) {
        super.onViewRecycled(holder)
        holder.onViewRecycled()
    }

}
