package com.syauqi.watcheez.core.data.source.network.api.people

import com.syauqi.watcheez.core.data.source.network.response.BaseResponse
import com.syauqi.watcheez.core.data.source.network.response.people.PeopleResponse
import com.syauqi.watcheez.core.data.source.network.response.people.people_detail.PersonDetailResponse

interface ApiHelperPeople {
    suspend fun getTrendingPeople(timeWindow: String) : BaseResponse<PeopleResponse>
    suspend fun getPopularPeople(): BaseResponse<PeopleResponse>
    suspend fun getPersonById(id: Int): PersonDetailResponse
    suspend fun searchPeopleByQuery(query: String): BaseResponse<PeopleResponse>
}