package com.izanrodrigo.fintonictestchallenge.util.extensions

import android.content.Context
import android.graphics.Rect
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

/* Source: http://stackoverflow.com/a/27037230 */
fun RecyclerView.addVerticalSpaceBetweenItems(heightPx: Int) {
    addItemDecoration(object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val adapter = parent.adapter ?: return
            if (parent.getChildAdapterPosition(view) != adapter.itemCount - 1) {
                outRect.bottom = heightPx
            }
        }
    })
}

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
