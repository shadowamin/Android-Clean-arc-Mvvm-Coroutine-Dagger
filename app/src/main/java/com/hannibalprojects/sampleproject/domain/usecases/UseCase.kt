package com.hannibalprojects.sampleproject.domain.usecases

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class UseCase<T> {

    private var parentJob: Job = Job()
    private var backgroundContext: CoroutineContext = Dispatchers.IO
    private var forgroundContext: CoroutineContext = Dispatchers.Main

    abstract suspend fun executeTask(): T

    fun execute(block: (T) -> Unit) {
        unsubscribe()
        parentJob = Job()
        CoroutineScope(forgroundContext + parentJob).launch {
            val result = withContext(backgroundContext) {
                executeTask()
            }
            block(result)
        }
    }

    private fun unsubscribe() {
        parentJob.apply {
            cancelChildren()
            cancel()
        }
    }


}