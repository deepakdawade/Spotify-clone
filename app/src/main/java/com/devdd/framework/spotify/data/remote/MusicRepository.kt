package com.devdd.framework.spotify.data.remote

import com.devdd.framework.spotify.data.local.entities.Music
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * Created by @author Deepak Dawade on 9/20/2020 at 10:20 PM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/
class MusicRepository {
    private val firebase = FirebaseFirestore.getInstance()
    private val songCollection = firebase.collection(FirebaseConstants.SONG_COLLECTION)

    suspend fun getAllSongs(): List<Music> {
        return try {
            songCollection.get().await().toObjects(Music::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }
}