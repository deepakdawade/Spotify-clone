package com.devdd.framework.spotify.data.local.objectbox

import com.devdd.framework.spotify.utils.extensions.listOrNull
import io.objectbox.Box
import io.objectbox.query.Query
import io.objectbox.query.QueryBuilder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn

/**
 * Created by @author Deepak Dawade on 9/20/2020 at 4:32 PM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/
abstract class SpotifyBox <T> {

    protected abstract val box: Box<T>

    fun query(): QueryBuilder<T> = box.query()

    fun tableData(): List<T>? = box.all.listOrNull()

    fun isTableEmpty(): Boolean = box.isEmpty

    fun entityOrNull(uniqueQuery: QueryBuilder<T>): T? = uniqueQuery.build().findUnique()

    fun firstEntityOrNull(): T? = tableData()?.firstOrNull()

    @ExperimentalCoroutinesApi
    fun observeBox(query: Query<T> = query().build(), dispatcher: CoroutineDispatcher) =
        (channelFlow {
            val subscription = query.subscribe().observer {
                offer(it)
            }
            awaitClose {
                subscription.cancel()
            }
        }).flowOn(dispatcher)

    @Synchronized
    open fun updateEntry(entity: T): Long {
        return box.put(entity)
    }

    @Synchronized
    open fun updateTable(entities: List<T>?) {
        if (entities.isNullOrEmpty().not()) box.put(entities)
    }

    @Synchronized
    fun removeEntity(obId: Long): Boolean {
        return box.remove(obId)
    }

    @Synchronized
    fun removeTable() {
        return box.removeAll()
    }
}

