package com.syauqi.watcheez.core.data.source.network.api

import com.syauqi.watcheez.core.data.source.network.response.people.BaseResponse
import com.syauqi.watcheez.core.data.source.network.response.people.PeopleResponse

interface ApiHelper {
    suspend fun getTrendingPeople(timeWindow: String) : BaseResponse<PeopleResponse>
    suspend fun getPopularPeople(): BaseResponse<PeopleResponse>
}