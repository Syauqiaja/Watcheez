package com.syauqi.watcheez.core.data

import android.annotation.SuppressLint
import com.syauqi.watcheez.core.data.source.network.response.ApiResponse
import com.syauqi.watcheez.utils.AppExecutors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

@SuppressLint("CheckResult")
abstract class NetworkBoundResource<ResultType, RequestType> {
    private var result : Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        val dbSource = loadFromDB().first()
        if(shouldFetch(dbSource)){
            emit(Resource.Loading())
            when(val apiResponse = createCall().first()){
                is ApiResponse.Success -> {
                    saveCallResult(apiResponse.data)
                    emitAll(loadFromDB().map { Resource.Success(it) })
                }
                is ApiResponse.Empty -> {
                    emitAll(loadFromDB().map { Resource.Success(it) })
                }
                is ApiResponse.Error ->{
                    onFetchFailed()
                    emit(Resource.Error(apiResponse.errorMessage))
                }
            }
        }else{
            emitAll(loadFromDB().map { Resource.Success(it) })
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    protected abstract suspend fun saveCallResult(response: RequestType)

    fun asFlow(): Flow<Resource<ResultType>> = result

}