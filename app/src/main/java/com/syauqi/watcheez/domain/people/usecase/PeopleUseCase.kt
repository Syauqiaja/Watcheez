package com.syauqi.watcheez.domain.people.usecase

import com.syauqi.watcheez.core.data.Resource
import com.syauqi.watcheez.core.data.source.network.response.ApiResponse
import com.syauqi.watcheez.domain.people.model.People
import com.syauqi.watcheez.domain.people.model.PersonDetail
import kotlinx.coroutines.flow.Flow

interface PeopleUseCase {
    fun getPopularPeople() : Flow<Resource<List<People>>>
    fun getTrendingPeople(): Flow<Resource<List<People>>>
    fun getPeopleById(id: Int): Flow<Resource<PersonDetail?>>
    fun searchPeopleByQuery(query: String): Flow<Resource<List<People>>>
    fun setPeopleFavorite(people: People)
    fun getFavoritePeople(): Flow<Resource<List<People>>>
}