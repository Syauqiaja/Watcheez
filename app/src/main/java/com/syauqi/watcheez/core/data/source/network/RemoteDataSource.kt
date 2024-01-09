package com.syauqi.watcheez.core.data.source.network

import android.util.Log
import com.syauqi.watcheez.core.data.source.network.api.ApiHelper
import com.syauqi.watcheez.core.data.source.network.response.ApiResponse
import com.syauqi.watcheez.core.data.source.network.response.movie.MovieDetailResponse
import com.syauqi.watcheez.core.data.source.network.response.movie.MovieResponse
import com.syauqi.watcheez.core.data.source.network.response.people.BaseResponse
import com.syauqi.watcheez.core.data.source.network.response.people.PeopleResponse
import com.syauqi.watcheez.core.data.source.network.response.people_detail.PersonDetailResponse
import com.syauqi.watcheez.domain.people.model.People
import com.syauqi.watcheez.domain.people.model.PersonDetail
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
class RemoteDataSource @Inject constructor(
    private val apiHelper: ApiHelper
) {
    fun getTrendingPeople(): Flow<ApiResponse<BaseResponse<PeopleResponse>>> {
        return flow{
            try {
                val response = apiHelper.getTrendingPeople("day")
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
                val response = apiHelper.getPopularPeople()
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
                val response = apiHelper.getPersonById(id)
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
                val response = apiHelper.searchPeopleByQuery(query)
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
    fun getTrendingMovies(): Flow<ApiResponse<BaseResponse<MovieResponse>>>{
        return flow {
            try {
                Log.w("RemoteDataSource", "getTrendingRunning")
                val response = apiHelper.getTrendingMovies()
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
    fun getMovieById(id: Int): Flow<ApiResponse<MovieDetailResponse>>{
        return flow {
            try {
                val response = apiHelper.getMovieById(id)
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