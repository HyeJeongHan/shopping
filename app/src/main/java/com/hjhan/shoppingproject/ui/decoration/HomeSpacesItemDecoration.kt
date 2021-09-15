package com.hjhan.shoppingproject.ui.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


class HomeSpacesItemDecoration(private val spanCount: Int, private val spacing: Int) : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position: Int = parent.getChildAdapterPosition(view)
        if (parent.adapter?.getItemViewType(position) == 0) {
            outRect.bottom = spacing
            return
        }

        val column: Int = position % spanCount
        outRect.left = spacing - column * spacing / spanCount
        outRect.right = (column + 1) * spacing / spanCount
        outRect.bottom = spacing
    }

}