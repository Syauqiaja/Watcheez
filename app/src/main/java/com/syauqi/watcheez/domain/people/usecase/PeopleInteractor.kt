package com.syauqi.watcheez.domain.people.usecase

import com.syauqi.watcheez.core.data.Resource
import com.syauqi.watcheez.core.data.source.network.response.ApiResponse
import com.syauqi.watcheez.domain.people.model.People
import com.syauqi.watcheez.domain.people.model.PersonDetail
import com.syauqi.watcheez.domain.people.repository.IPeopleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PeopleInteractor @Inject constructor(private val repository: IPeopleRepository) :
    PeopleUseCase {
    override fun getPopularPeople(): Flow<Resource<List<People>>> =
        repository.getPopularPeople()

    override fun getTrendingPeople(): Flow<Resource<List<People>>> =
        repository.getTrendingPeople()

    override fun getPeopleById(id: Int): Flow<Resource<PersonDetail?>> =
        repository.getPeopleById(id)

    override fun searchPeopleByQuery(query: String): Flow<Resource<List<People>>> =
        repository.searchPeopleByQuery(query)

}