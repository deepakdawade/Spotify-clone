package com.devdd.framework.spotify.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.devdd.framework.spotify.R
import com.devdd.framework.spotify.data.entities.Song
import com.devdd.framework.spotify.data.entities.SongDiffUtil
import com.devdd.framework.spotify.databinding.ListItemBinding
import com.devdd.framework.spotify.utils.extensions.bindWithLayout
import javax.inject.Inject


/**
 * Created by @author Deepak Dawade on 10/18/2020 at 2:54 PM.
 * Copyright (c) 2020 deepak.dawade.dd@gmail.com All rights reserved.
 */
class SongAdapter constructor(
    private val glide: RequestManager,
    private val onItemClickListener: (Song) -> Unit = {},
) : ListAdapter<Song, SongAdapter.SongViewHolder>(SongDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        return SongViewHolder(bindWithLayout(R.layout.list_item, parent))
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class SongViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.apply {
                requestManager = glide
                this.song = song
                root.setOnClickListener {
                    onItemClickListener(song)
                }
                executePendingBindings()
            }
        }
    }
}