package com.syauqi.watcheez.domain.usecase

import com.syauqi.watcheez.core.data.source.network.response.ApiResponse
import com.syauqi.watcheez.domain.model.People
import com.syauqi.watcheez.domain.model.PersonDetail
import com.syauqi.watcheez.domain.repository.IPeopleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PeopleInteractor @Inject constructor(private val repository: IPeopleRepository) : PeopleUseCase {
    override fun getPopularPeople(): Flow<ApiResponse<List<People>>> =
        repository.getPopularPeople()

    override fun getTrendingPeople(): Flow<ApiResponse<List<People>>> =
        repository.getTrendingPeople()

    override fun getPeopleById(id: Int): Flow<ApiResponse<PersonDetail>> =
        repository.getPeopleById(id)

    override fun searchPeopleByQuery(query: String): Flow<ApiResponse<List<People>>> =
        repository.searchPeopleByQuery(query)

}