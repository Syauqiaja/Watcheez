package com.syauqi.watcheez.core.data.source.network.datasource

import com.syauqi.watcheez.core.data.source.network.response.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.lang.Exception

abstract class RemoteDataSource<ResponseType> {
    private var result: Flow<ApiResponse<ResponseType>> = flow{
        try {
            val response = fetchFromApi()
            if(emptyWhen(response)){
                emit(ApiResponse.Empty)
            }else if(successWhen(response)){
                emit(ApiResponse.Success(response))
            }
        }catch (e: HttpException){
            e.printStackTrace()
            emit(ApiResponse.Error(e.message.toString()))
        }catch (e: Exception){
            e.printStackTrace()
            emit(ApiResponse.Error(e.message.toString()))
        }
    }
    protected abstract suspend fun fetchFromApi(): ResponseType
    protected open fun successWhen(response: ResponseType): Boolean {return true}
    protected open fun emptyWhen(response: ResponseType): Boolean {return false}
    fun asFlow(): Flow<ApiResponse<ResponseType>> = result.flowOn(Dispatchers.IO)
}