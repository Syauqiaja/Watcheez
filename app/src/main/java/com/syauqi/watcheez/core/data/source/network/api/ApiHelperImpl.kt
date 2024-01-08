package com.syauqi.watcheez.core.data.source.network.api

import com.syauqi.watcheez.core.data.source.network.response.people.BaseResponse
import com.syauqi.watcheez.core.data.source.network.response.people.PeopleResponse
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
): ApiHelper {
    override suspend fun getTrendingPeople(timeWindow: String) = apiService.getTrendingPeople(timeWindow)
    override suspend fun getPopularPeople(): BaseResponse<PeopleResponse> = apiService.getPopularPeople()
}