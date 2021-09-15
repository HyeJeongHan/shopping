package com.hjhan.shoppingproject.ui.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


class LikeSpacesItemDecoration(private val spanCount: Int, private val spacing: Int) :
    ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position: Int = parent.getChildAdapterPosition(view)
        val column: Int = position % spanCount

        outRect.left =
            spacing - column * spacing / spanCount
        outRect.right =
            (column + 1) * spacing / spanCount
        if (position < spanCount) {
            outRect.top = spacing
        }
        outRect.bottom = spacing

    }

}