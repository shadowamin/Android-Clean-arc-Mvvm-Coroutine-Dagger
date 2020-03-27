package com.hannibalprojects.sampleproject.presentation

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.hannibalprojects.sampleproject.domain.Repository
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import com.hannibalprojects.sampleproject.presentation.di.Provider

class DownloadWorker(
    val context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    @Inject
    lateinit var repository: Repository
    init {
        Provider.appComponent?.inject(this)
    }

    override suspend fun doWork(): Result = coroutineScope {
        val response = repository.refreshUsers()
        if (response.code == 200) {
            Result.success()
        } else {
            Result.failure()
        }
    }

}