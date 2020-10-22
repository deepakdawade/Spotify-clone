package com.devdd.framework.spotify.adapters

import androidx.recyclerview.widget.AsyncListDiffer
import com.bumptech.glide.RequestManager
import com.devdd.framework.spotify.R
import javax.inject.Inject

class SongAdapter @Inject constructor(glide: RequestManager) : BaseSongAdapter(R.layout.list_item,glide) {
    override val differ = AsyncListDiffer(this, diffCallback)
}
