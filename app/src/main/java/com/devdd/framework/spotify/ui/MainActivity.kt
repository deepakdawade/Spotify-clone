package com.devdd.framework.spotify.ui

import android.os.Bundle
import com.bumptech.glide.RequestManager
import com.devdd.framework.spotify.R
import com.devdd.framework.spotify.ui.base.SpotifyActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : SpotifyActivity(R.layout.activity_main) {

    @Inject
    lateinit var requestManager: RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
