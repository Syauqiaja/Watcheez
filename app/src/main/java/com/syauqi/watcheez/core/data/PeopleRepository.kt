package com.syauqi.watcheez.core.data

import android.util.Log
import com.syauqi.watcheez.core.data.source.network.api.ApiHelper
import com.syauqi.watcheez.core.data.source.network.response.ApiResponse
import com.syauqi.watcheez.domain.model.People
import com.syauqi.watcheez.domain.model.PersonDetail
import com.syauqi.watcheez.domain.repository.IPeopleRepository
import com.syauqi.watcheez.utils.DataMapper.toPeopleArrayList
import com.syauqi.watcheez.utils.DataMapper.toPersonDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PeopleRepository @Inject constructor(
    private val apiHelper: ApiHelper
): IPeopleRepository {
    override fun getTrendingPeople(): Flow<ApiResponse<List<People>>>{
        return flow{
            try {
                val response = apiHelper.getTrendingPeople("day")
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
    override fun getPopularPeople() : Flow<ApiResponse<List<People>>> {
        return flow{
            try {
                val response = apiHelper.getPopularPeople()
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
    override fun getPeopleById(id: Int): Flow<ApiResponse<PersonDetail>>{
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

    override fun searchPeopleByQuery(query: String): Flow<ApiResponse<List<People>>> {
        return flow {
            try {
                val response = apiHelper.searchPeopleByQuery(query)
                emit(ApiResponse.Success(response.results.toPeopleArrayList()))
            }catch (e: HttpException){
                Log.e("MovieRepository", e.message.toString())
                emit(ApiResponse.Error(e.message.toString()))
            }catch (e: Exception){
                Log.e("MovieRepository", e.message.toString())
            }
        }
    }
}