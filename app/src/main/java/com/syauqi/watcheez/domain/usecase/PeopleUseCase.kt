package com.syauqi.watcheez.domain.usecase

import com.syauqi.watcheez.core.data.source.network.response.ApiResponse
import com.syauqi.watcheez.domain.model.People
import com.syauqi.watcheez.domain.model.PersonDetail
import kotlinx.coroutines.flow.Flow

interface PeopleUseCase {
    fun getTrendingPeople() : Flow<ApiResponse<List<People>>>
    fun getPopularPeople(): Flow<ApiResponse<List<People>>>
    fun getPeopleById(id: Int): Flow<ApiResponse<PersonDetail>>
}