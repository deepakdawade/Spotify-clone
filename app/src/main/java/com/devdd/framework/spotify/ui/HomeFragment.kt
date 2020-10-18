package com.devdd.framework.spotify.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import com.bumptech.glide.RequestManager
import com.devdd.framework.spotify.R
import com.devdd.framework.spotify.databinding.FragmentHomeBinding
import com.devdd.framework.spotify.ui.base.SpotifyFragment
import com.devdd.framework.spotify.utils.result.Status
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


/**
 * Created by @author Deepak Dawade on 10/18/2020 at 3:13 PM.
 * Copyright (c) 2020 deepak.dawade.dd@gmail.com All rights reserved.
 *
 */
@AndroidEntryPoint
class HomeFragment:SpotifyFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    @Inject
    lateinit var requestManager: RequestManager
    private val adapter by lazy {
        SongAdapter(requestManager){
            activityViewModel.playOrToggleSong(it)
        }
    }
    private val activityViewModel by activityViewModels<MainViewModel>()
    override fun onViewCreated(binding: FragmentHomeBinding, savedInstanceState: Bundle?) {
        binding.rvAllSongs.adapter = adapter
    }

    override fun observeLiveData() {
        activityViewModel.mediaItems.observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS -> {
                    binding.allSongsProgressBar.isVisible = false
                    adapter.submitList(it.data)
                }
                Status.ERROR -> Unit
                Status.LOADING -> binding.allSongsProgressBar.isVisible = true
            }
        }
    }
}