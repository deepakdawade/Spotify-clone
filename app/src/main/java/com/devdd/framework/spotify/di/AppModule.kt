package com.devdd.framework.spotify.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.devdd.framework.spotify.R
import com.devdd.framework.spotify.exoplayer.MusicServiceConnection
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

/**
 * Created by @author Deepak Dawade on 9/20/2020 at 8:58 PM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/
@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ): RequestManager = Glide.with(context).setDefaultRequestOptions(
        RequestOptions().placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_image)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
    )

    @Singleton
    @Provides
    fun provideMusicServiceConnection(
        @ApplicationContext context: Context
    ): MusicServiceConnection = MusicServiceConnection(context)
}
