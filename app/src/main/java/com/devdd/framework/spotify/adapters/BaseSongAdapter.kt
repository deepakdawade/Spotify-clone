package com.devdd.framework.spotify.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.devdd.framework.spotify.R
import com.devdd.framework.spotify.data.entities.Song
import com.devdd.framework.spotify.databinding.ListItemBinding
import com.devdd.framework.spotify.databinding.SwipeItemBinding
import com.devdd.framework.spotify.utils.databindingextenstion.bindWithLayout

abstract class BaseSongAdapter(
    private val layoutId: Int,
    private val glide: RequestManager? = null,
) : RecyclerView.Adapter<BaseSongAdapter.BaseSongViewHolder>() {

    sealed class BaseSongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        class SongViewHolder(val binding: ListItemBinding) : BaseSongViewHolder(binding.root) {
            fun bind(song: Song, glide: RequestManager, onItemClickListener: ((Song) -> Unit)) {
                binding.apply {
                    this.song = song
                    glide.load(song.imageUrl).into(ivItemImage)

                    root.setOnClickListener {
                        onItemClickListener(song)
                    }
                    executePendingBindings()
                }
            }
        }

        class SwipeSongViewHolder(val binding: SwipeItemBinding) :
            BaseSongViewHolder(binding.root) {
            fun bind(song: Song, onItemClickListener: (Song) -> Unit) {
                binding.apply {
                    this.song = song
                    root.setOnClickListener {
                        onItemClickListener(song)
                    }
                    executePendingBindings()
                }
            }
        }
    }

    protected val diffCallback = object : DiffUtil.ItemCallback<Song>() {
        override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem.mediaId == newItem.mediaId
        }

        override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    protected abstract val differ: AsyncListDiffer<Song>

    var songs: List<Song>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseSongViewHolder {
        return when (layoutId) {
            R.layout.list_item -> BaseSongViewHolder.SongViewHolder(bindWithLayout(layoutId,
                parent))
            R.layout.swipe_item -> BaseSongViewHolder.SwipeSongViewHolder(bindWithLayout(
                layoutId,
                parent))
            else -> throw IllegalStateException("unknown viewtype $viewType")
        }
    }

    override fun onBindViewHolder(holder: BaseSongViewHolder, position: Int) {
        val song = songs[position]
        when (holder) {
            is BaseSongViewHolder.SwipeSongViewHolder -> holder.bind(song, onItemClickListener)
            is BaseSongViewHolder.SongViewHolder -> holder.bind(song,
                requireNotNull(glide), onItemClickListener)
        }
    }

    protected var onItemClickListener: ((Song) -> Unit) = {}

    fun setItemClickListener(listener: (Song) -> Unit) {
        onItemClickListener = listener
    }

    override fun getItemCount(): Int {
        return songs.size
    }
}

