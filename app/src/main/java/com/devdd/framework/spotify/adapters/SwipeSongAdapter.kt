package com.devdd.framework.spotify.adapters

import androidx.recyclerview.widget.AsyncListDiffer
import com.devdd.framework.spotify.R

class SwipeSongAdapter : BaseSongAdapter(R.layout.swipe_item) {
    override val differ = AsyncListDiffer(this, diffCallback)
}



















