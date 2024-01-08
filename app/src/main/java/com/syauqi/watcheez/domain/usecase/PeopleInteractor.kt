package com.syauqi.watcheez.domain.usecase

import com.syauqi.watcheez.core.data.source.network.response.ApiResponse
import com.syauqi.watcheez.domain.model.People
import com.syauqi.watcheez.domain.model.PersonDetail
import com.syauqi.watcheez.domain.repository.IPeopleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PeopleInteractor @Inject constructor(private val peopleRepository: IPeopleRepository) : PeopleUseCase {
    override fun getTrendingPeople(): Flow<ApiResponse<List<People>>> =
        peopleRepository.getTrendingPeople()

    override fun getPopularPeople(): Flow<ApiResponse<List<People>>> =
        peopleRepository.getPopularPeople()

    override fun getPeopleById(id: Int): Flow<ApiResponse<PersonDetail>> =
        peopleRepository.getPeopleById(id)

}