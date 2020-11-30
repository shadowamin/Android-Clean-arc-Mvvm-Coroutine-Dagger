package com.hannibalprojects.sampleproject.domain.usecases

import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

typealias CompletionBlock<T> = UseCase.Request<T>.() -> Unit
abstract class UseCase<T> {
    private var parentJob: Job = Job()
    private var backgroundContext: CoroutineContext = Dispatchers.IO
    private var forgroundContext: CoroutineContext = Dispatchers.Main

    abstract suspend fun executeTask(): T

    fun execute(block: CompletionBlock<T>) {
        val response = Request<T>().apply { block() }
        unsubscribe()
        parentJob = Job()
        CoroutineScope(forgroundContext + parentJob).launch {
            try {
                val result = withContext(backgroundContext) {
                    executeTask()
                }
                response(result)
            } catch (e: Exception) {
                response(e)
            }
        }
    }

    private fun unsubscribe() {
        parentJob.apply {
            cancelChildren()
            cancel()
        }
    }

    class Request<T> {
        private var onComplete: ((T) -> Unit)? = null
        private var onError: ((Exception) -> Unit)? = null

        fun onComplete(block: (T) -> Unit) {
            onComplete = block
        }

        fun onError(block: (Exception) -> Unit) {
            onError = block
        }

        operator fun invoke(result: T) {
            onComplete?.let {
                it.invoke(result)
            }
        }

        operator fun invoke(error: Exception) {
            onError?.let {
                it.invoke(error)
            }
        }
    }
}