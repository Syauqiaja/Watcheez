package com.syauqi.watcheez.core.data.source.network.datasource

import android.util.Log
import com.syauqi.watcheez.core.data.source.network.api.people.ApiHelperPeople
import com.syauqi.watcheez.core.data.source.network.response.ApiResponse
import com.syauqi.watcheez.core.data.source.network.response.BaseResponse
import com.syauqi.watcheez.core.data.source.network.response.people.PeopleResponse
import com.syauqi.watcheez.core.data.source.network.response.people.people_detail.PersonDetailResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDSPeople @Inject constructor(
    private val apiHelperPeople: ApiHelperPeople
) {
    fun getTrendingPeople(): Flow<ApiResponse<BaseResponse<PeopleResponse>>> {
        return flow{
            try {
                val response = apiHelperPeople.getTrendingPeople("day")
                val dataArray = response.results
                if(dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response))
                }else{
                    emit(ApiResponse.Empty)
                }
            }catch (e: Exception){
                e.printStackTrace()
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getPopularPeople() : Flow<ApiResponse<BaseResponse<PeopleResponse>>> {
        return flow{
            try {
                val response = apiHelperPeople.getPopularPeople()
                val dataArray = response.results
                if(dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response))
                }else{
                    emit(ApiResponse.Empty)
                }
            }catch (e: Exception){
                Log.e("RemoteDataSource", e.message.toString())
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getPeopleById(id: Int): Flow<ApiResponse<PersonDetailResponse>>{
        return flow {
            try {
                val response = apiHelperPeople.getPersonById(id)
                emit(ApiResponse.Success(response))
            }catch (e: Exception){
                Log.e("RemoteDataSource", e.message.toString())
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun searchPeopleByQuery(query: String): Flow<ApiResponse<BaseResponse<PeopleResponse>>> {
        return flow {
            try {
                val response = apiHelperPeople.searchPeopleByQuery(query)
                emit(ApiResponse.Success(response))
            }catch (e: HttpException){
                e.printStackTrace()
                emit(ApiResponse.Error(e.message.toString()))
            }catch (e: Exception){
                e.printStackTrace()
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}