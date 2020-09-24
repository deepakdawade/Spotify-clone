package com.devdd.framework.spotify.utils.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import com.devdd.framework.spotify.utils.result.Result
/**
 * Created by @author Deepak Dawade on 9/20/2020 at 7:53 PM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/

abstract class FlowUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {
    /**
     * Executes business logic in its execute method and keep posting updates to the result as
     * [Result<R>].
     * Handling an exception (emit [Result.Error] to the result) is the subclasses's responsibility.
     */

    @ExperimentalCoroutinesApi
    operator fun invoke(parameters: P): Flow<Result<R>> {
        return execute(parameters)
            .catch { e ->
                Timber.d("Error throe by flow ${e.message}")
                emit(
                    Result.Error(
                        Exception(e)
                    )
                )
            }
            .flowOn(coroutineDispatcher)
    }

    abstract fun execute(parameters: P): Flow<Result<R>>

}

operator fun <T> FlowUseCase<Unit, T>.invoke() = invoke(Unit)
