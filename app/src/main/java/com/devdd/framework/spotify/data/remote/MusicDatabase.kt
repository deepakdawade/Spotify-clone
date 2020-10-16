package com.devdd.framework.spotify.data.remote

import com.devdd.framework.spotify.data.entities.Song
import com.devdd.framework.spotify.exoplayer.MusicContants.SONG_COLLECTION
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


/**
 * Created by @author Deepak Dawade on 10/16/2020 at 10:25 PM.
 * Copyright (c) 2020 deepak.dawade.dd@gmail.com All rights reserved.
 *
 */
class MusicDatabase {
    private val firestore = FirebaseFirestore.getInstance()
    private val songCollection = firestore.collection(SONG_COLLECTION)
    suspend fun getAllSongs():List<Song>{
       return try {
            songCollection.get().await().toObjects(Song::class.java)
        }catch (ex:Exception){
            emptyList()
        }
    }
}