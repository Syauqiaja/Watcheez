package com.syauqi.watcheez.core.data

import android.annotation.SuppressLint
import com.syauqi.watcheez.core.data.source.network.response.ApiResponse
import com.syauqi.watcheez.utils.AppExecutors
import kotlinx.coroutines.flow.Flow

@SuppressLint("CheckResult")
abstract class NetworkBoundResource<ResultType, RequestType> constructor(
    private val appExecutors: AppExecutors
) {

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract fun createCall(): Flow<ApiResponse<RequestType>>

    protected abstract fun saveCallResult(data: RequestType)

}