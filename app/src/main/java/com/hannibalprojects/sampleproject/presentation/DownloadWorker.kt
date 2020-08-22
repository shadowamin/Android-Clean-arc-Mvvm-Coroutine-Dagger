package com.hannibalprojects.sampleproject.presentation

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.coroutineScope

class DownloadWorker(
    val context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {


    override suspend fun doWork(): Result = coroutineScope {
            Result.success()
    }

}