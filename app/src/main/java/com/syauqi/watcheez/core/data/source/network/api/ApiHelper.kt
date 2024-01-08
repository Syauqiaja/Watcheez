package com.syauqi.watcheez.core.data.source.network.api

import com.syauqi.watcheez.core.data.source.network.response.people.BaseResponse
import com.syauqi.watcheez.core.data.source.network.response.people.PeopleDetailResponse
import com.syauqi.watcheez.core.data.source.network.response.people.PeopleResponse
import com.syauqi.watcheez.core.data.source.network.response.people_detail.PersonDetailResponse

interface ApiHelper {
    suspend fun getTrendingPeople(timeWindow: String) : BaseResponse<PeopleResponse>
    suspend fun getPopularPeople(): BaseResponse<PeopleResponse>
    suspend fun getPersonById(id: Int): PersonDetailResponse
}