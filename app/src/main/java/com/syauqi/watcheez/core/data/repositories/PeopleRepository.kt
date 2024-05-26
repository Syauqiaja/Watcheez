package com.syauqi.watcheez.core.data.repositories

import com.syauqi.watcheez.core.data.NetworkBoundResource
import com.syauqi.watcheez.core.data.Resource
import com.syauqi.watcheez.core.data.source.local.LocalDSPeople
import com.syauqi.watcheez.core.data.source.network.datasource.RemoteDSPeople
import com.syauqi.watcheez.core.data.source.network.response.ApiResponse
import com.syauqi.watcheez.core.data.source.network.response.BaseResponse
import com.syauqi.watcheez.core.data.source.network.response.people.PeopleResponse
import com.syauqi.watcheez.domain.people.model.People
import com.syauqi.watcheez.domain.people.model.PersonDetail
import com.syauqi.watcheez.domain.people.repository.IPeopleRepository
import com.syauqi.watcheez.utils.DataMapper.toPeopleArrayList
import com.syauqi.watcheez.utils.DataMapper.toPeopleEntity
import com.syauqi.watcheez.utils.DataMapper.toPeopleEntityArrayList
import com.syauqi.watcheez.utils.DataMapper.toPersonDetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PeopleRepository @Inject constructor(
    private val remoteDSPeople: RemoteDSPeople,
    private val localDSPeople: LocalDSPeople,
): IPeopleRepository {
    override fun getTrendingPeople(): Flow<Resource<List<People>>>{
        return flow<Resource<List<People>>>{
            emit(Resource.Loading())

            val response = remoteDSPeople.getTrendingPeople()
            response.collect{result ->
                when(result){
                    is ApiResponse.Success -> {
                        val data = result.data.results.toPeopleEntityArrayList()
                        localDSPeople.insertAlPeople(data)
                        emit(Resource.Success(data.toPeopleArrayList()))
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
                return localDSPeople.getAllPeoples().map {
                    it.toPeopleArrayList()
                }
            }

            override suspend fun createCall(): Flow<ApiResponse<BaseResponse<PeopleResponse>>> {
                return remoteDSPeople.getPopularPeople()
            }
            override suspend fun saveCallResult(response: BaseResponse<PeopleResponse>) {
                val listPeopleEntity = response.results.toPeopleEntityArrayList()
                localDSPeople.insertAlPeople(listPeopleEntity)
            }
            override fun shouldFetch(data: List<People>?): Boolean = data.isNullOrEmpty()
        }.asFlow()

    override fun getPeopleById(id: Int): Flow<Resource<PersonDetail?>>{
        return flow<Resource<PersonDetail?>> {
            emit(Resource.Loading())

            val response = remoteDSPeople.getPeopleById(id)
            response.collect {result ->
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
                return localDSPeople.searchPeopleByQuery("$query%").map {
                    it.toPeopleArrayList()
                }
            }
            override suspend fun createCall(): Flow<ApiResponse<BaseResponse<PeopleResponse>>> {
                return remoteDSPeople.searchPeopleByQuery(query)
            }
            override suspend fun saveCallResult(response: BaseResponse<PeopleResponse>) {
                val listPeopleEntity = response.results.toPeopleEntityArrayList()
                localDSPeople.insertAlPeople(listPeopleEntity)
            }
            override fun shouldFetch(data: List<People>?): Boolean = true
        }.asFlow()

    override fun setPeopleFavorite(people: People) {
        CoroutineScope(Dispatchers.IO).launch {
            localDSPeople.setPeopleFavorite(people.toPeopleEntity())
        }
    }

    override fun getFavoritePeople(): Flow<Resource<List<People>>> {
        return flow<Resource<List<People>>> {
            emit(Resource.Loading())
            try {
                localDSPeople.getFavoritePeople().collect(){
                    if(it.isNotEmpty()) {
                        emit(Resource.Success(it.toPeopleArrayList()))
                    }else{
                        emit(Resource.Error("Data not found"))
                    }
                }
            }catch (e:Exception){
                emit(Resource.Error("Data not found"))
            }
        }.flowOn(Dispatchers.IO)
    }
}