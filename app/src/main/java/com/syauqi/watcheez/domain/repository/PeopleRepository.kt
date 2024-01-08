package com.syauqi.watcheez.domain.repository

import android.util.Log
import com.syauqi.watcheez.core.data.source.network.api.ApiHelper
import com.syauqi.watcheez.core.data.source.network.response.ApiResponse
import com.syauqi.watcheez.domain.models.People
import com.syauqi.watcheez.domain.models.PersonDetail
import com.syauqi.watcheez.utils.DataMapper.toPeopleArrayList
import com.syauqi.watcheez.utils.DataMapper.toPersonDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class PeopleRepository(
    private val apiHelper: ApiHelper
) {
    fun getPopularPeople(): Flow<ApiResponse<List<People>>>{
        return flow{
            try {
                val response = apiHelper.getPopularPeople()
                val dataArray = response.results.subList(0,4).toPeopleArrayList()
                if(dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(dataArray))
                }else{
                    emit(ApiResponse.Empty)
                }
            }catch (e: Exception){
                Log.e("MovieRepository", e.message.toString())
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
    fun getTrendingPeople() : Flow<ApiResponse<List<People>>> {
        return flow{
            try {
                val response = apiHelper.getTrendingPeople("day")
                val dataArray = response.results.toPeopleArrayList()
                if(dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(dataArray))
                }else{
                    emit(ApiResponse.Empty)
                }
            }catch (e: Exception){
                Log.e("MovieRepository", e.message.toString())
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
    fun getPeopleById(id: Int): Flow<ApiResponse<PersonDetail>>{
        return flow {
            try {
                val response = apiHelper.getPersonById(id)
                emit(ApiResponse.Success(response.toPersonDetail()))
            }catch (e: Exception){
                Log.e("MovieRepository", e.message.toString())
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}