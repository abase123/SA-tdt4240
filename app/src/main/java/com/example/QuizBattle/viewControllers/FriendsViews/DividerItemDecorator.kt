package com.example.QuizBattle.viewControllers.FriendsViews

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
/**
 * The DividerItemDecorator class is a custom RecyclerView.ItemDecoration used to apply spacing
 * between items within a RecyclerView in the QuizBattle application. It takes a divider width as input,
 * which determines the size of the space between items.
 */
class DividerItemDecorator(private val dividerWidth: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.right = dividerWidth
    }
}