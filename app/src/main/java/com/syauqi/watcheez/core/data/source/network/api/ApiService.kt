package com.syauqi.watcheez.core.data.source.network.api

import com.syauqi.watcheez.core.data.source.network.response.people.BaseResponse
import com.syauqi.watcheez.core.data.source.network.response.people.PeopleDetailResponse
import com.syauqi.watcheez.core.data.source.network.response.people.PeopleResponse
import com.syauqi.watcheez.core.data.source.network.response.people_detail.PersonDetailResponse
import com.syauqi.watcheez.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("trending/person/{time_window}")
    suspend fun getTrendingPeople(
        @Path("time_window") timeWindow: String,
        @Query("api_key") apiKey : String = Constants.API_KEY
    ): BaseResponse<PeopleResponse>

    @GET("person/popular")
    suspend fun getPopularPeople(
        @Query("api_key") apiKey : String = Constants.API_KEY
    ): BaseResponse<PeopleResponse>

    @GET("person/{person_id}")
    suspend fun getPersonById(
        @Path("person_id") personid : Int,
        @Query("api_key") apiKey : String = Constants.API_KEY,
        @Query("append_to_response") appendToResponse : String = "images,movie_credits,external_ids"
    ): PersonDetailResponse
}