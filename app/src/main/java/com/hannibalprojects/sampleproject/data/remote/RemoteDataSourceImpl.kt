package com.hannibalprojects.sampleproject.data.remote

import android.util.Log
import com.hannibalprojects.sampleproject.domain.User
import java.lang.Exception
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(val userApi: UserApi) : RemoteDataSource {
    companion object {
        const val TAG = " RemoteDataSourceImpl"
    }

    override suspend fun getUsers(): List<User>? {
        return try {
            val wsResponse = userApi.getUsers()
            wsResponse.isSuccessful
            if (wsResponse.isSuccessful) {
                wsResponse.body()?.data
            } else {
                Log.e(TAG, wsResponse.message())
                null
            }
        } catch (e: Exception) {
            Log.e(TAG, "${e.message}")
            null
        }
    }
}