package com.devdd.framework.spotify.ui

import androidx.activity.viewModels
import com.devdd.framework.spotify.R
import com.devdd.framework.spotify.databinding.ActivityMainBinding
import com.devdd.framework.spotify.ui.base.SpotifyActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : SpotifyActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val viewModel by viewModels<MainViewModel>()

    override fun setupView(binding: ActivityMainBinding) {

    }

    override fun observeLiveData() {

    }
}
