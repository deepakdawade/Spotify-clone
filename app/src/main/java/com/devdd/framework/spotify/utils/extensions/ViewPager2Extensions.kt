package com.devdd.framework.spotify.utils.extensions

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.devdd.framework.spotify.R
import kotlin.math.abs

/**
 * Created by @author Deepak Dawade on 9/20/2020 at 7:53 PM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/

fun ViewPager2.setPageHint() {
    // You need to retain one page on each side so that the next and previous items are visible
    offscreenPageLimit = 1

    // Add a PageTransformer that translates the next and previous items horizontally
    // towards the center of the screen, which makes them visible
    val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
    val currentItemHorizontalMarginPx = resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
    val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
    val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
        page.translationX = -pageTranslationX * position
        // Next line scales the item's height. You can remove it if you don't want this effect
        page.scaleY = 1 - 0.25f * abs(position)
        // If you want a fading effect uncomment the next line:
        // page.alpha = 0.25f + (1 - abs(position))
    }
    setPageTransformer(pageTransformer)

    // The ItemDecoration gives the current (centered) item horizontal margin so that
    // it doesn't occupy the whole screen width. Without it the items overlap
    val itemDecoration = HorizontalMarginItemDecoration(context, R.dimen.viewpager_current_item_horizontal_margin)
    addItemDecoration(itemDecoration)
}

class HorizontalMarginItemDecoration(context: Context?, viewpagerCurrentItemHorizontalMargin: Int) :
    RecyclerView.ItemDecoration() {

}
