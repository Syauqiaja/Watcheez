package com.syauqi.watcheez.core.data.source.network.api.people

import com.syauqi.watcheez.core.data.source.network.api.ApiService
import com.syauqi.watcheez.core.data.source.network.response.BaseResponse
import com.syauqi.watcheez.core.data.source.network.response.people.PeopleResponse
import com.syauqi.watcheez.core.data.source.network.response.people.people_detail.PersonDetailResponse
import javax.inject.Inject

class ApiHelperPeopleImpl @Inject constructor(
    private val apiService: ApiService
): ApiHelperPeople {
    override suspend fun getTrendingPeople(timeWindow: String) =
        apiService.getTrendingPeople(timeWindow)

    override suspend fun getPopularPeople(): BaseResponse<PeopleResponse> =
        apiService.getPopularPeople()

    override suspend fun getPersonById(id: Int): PersonDetailResponse = apiService.getPersonById(id)
    override suspend fun searchPeopleByQuery(query: String): BaseResponse<PeopleResponse> =
        apiService.searchPeopleByQuery(query)
}