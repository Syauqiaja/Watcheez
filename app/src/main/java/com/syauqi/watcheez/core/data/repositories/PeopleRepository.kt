package com.syauqi.watcheez.core.data.repositories

import com.syauqi.watcheez.core.data.NetworkBoundResource
import com.syauqi.watcheez.core.data.Resource
import com.syauqi.watcheez.core.data.source.local.LocalDataSource
import com.syauqi.watcheez.core.data.source.network.RemoteDataSource
import com.syauqi.watcheez.core.data.source.network.response.ApiResponse
import com.syauqi.watcheez.core.data.source.network.response.people.BaseResponse
import com.syauqi.watcheez.core.data.source.network.response.people.PeopleResponse
import com.syauqi.watcheez.domain.people.model.People
import com.syauqi.watcheez.domain.people.model.PersonDetail
import com.syauqi.watcheez.domain.people.repository.IPeopleRepository
import com.syauqi.watcheez.utils.AppExecutors
import com.syauqi.watcheez.utils.DataMapper.toPeopleArrayList
import com.syauqi.watcheez.utils.DataMapper.toPeopleEntityArrayList
import com.syauqi.watcheez.utils.DataMapper.toPersonDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PeopleRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): IPeopleRepository {
    override fun getTrendingPeople(): Flow<Resource<List<People>>>{
        return flow<Resource<List<People>>>{
            emit(Resource.Loading())

            val response = remoteDataSource.getTrendingPeople()
            response.map {result ->
                when(result){
                    is ApiResponse.Success -> {
                        val data = result.data.results.toPeopleArrayList()
                        emit(Resource.Success(data))
                    }
                    is ApiResponse.Empty -> {
                        emit(Resource.Success(ArrayList()))
                    }
                    is ApiResponse.Error -> {
                        emit(Resource.Error(result.errorMessage))
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
    }
    override fun getPopularPeople() : Flow<Resource<List<People>>> =
        object : NetworkBoundResource<List<People>, BaseResponse<PeopleResponse>>(){
            override fun loadFromDB(): Flow<List<People>> {
                return localDataSource.getAllPeoples().map {
                    it.toPeopleArrayList()
                }
            }

            override suspend fun createCall(): Flow<ApiResponse<BaseResponse<PeopleResponse>>> {
                return remoteDataSource.getPopularPeople()
            }
            override suspend fun saveCallResult(response: BaseResponse<PeopleResponse>) {
                val listPeopleEntity = response.results.toPeopleEntityArrayList()
                localDataSource.insertAlPeople(listPeopleEntity)
            }
            override fun shouldFetch(data: List<People>?): Boolean = true

        }.asFlow()
    override fun getPeopleById(id: Int): Flow<Resource<PersonDetail?>>{
        return flow<Resource<PersonDetail?>> {
            emit(Resource.Loading())

            val response = remoteDataSource.getPeopleById(id)
            response.map {result ->
                when(result){
                    is ApiResponse.Success -> {
                        val data = result.data.toPersonDetail()
                        emit(Resource.Success(data))
                    }
                    is ApiResponse.Empty -> {
                        emit(Resource.Success(null))
                    }
                    is ApiResponse.Error -> {
                        emit(Resource.Error(result.errorMessage))
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun searchPeopleByQuery(query: String): Flow<Resource<List<People>>> =
        object : NetworkBoundResource<List<People>, BaseResponse<PeopleResponse>>(){
            override fun loadFromDB(): Flow<List<People>> {
                return localDataSource.searchPeopleByQuery(query).map {
                    it.toPeopleArrayList()
                }
            }
            override suspend fun createCall(): Flow<ApiResponse<BaseResponse<PeopleResponse>>> {
                return remoteDataSource.searchPeopleByQuery(query)
            }
            override suspend fun saveCallResult(response: BaseResponse<PeopleResponse>) {
                val listPeopleEntity = response.results.toPeopleEntityArrayList()
                localDataSource.insertAlPeople(listPeopleEntity)
            }
            override fun shouldFetch(data: List<People>?): Boolean = true
        }.asFlow()
}