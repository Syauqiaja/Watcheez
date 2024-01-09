package com.syauqi.watcheez.core.data.source.network.api

import com.syauqi.watcheez.core.data.source.network.response.movie.MovieDetailResponse
import com.syauqi.watcheez.core.data.source.network.response.movie.MovieResponse
import com.syauqi.watcheez.core.data.source.network.response.people.BaseResponse
import com.syauqi.watcheez.core.data.source.network.response.people.PeopleDetailResponse
import com.syauqi.watcheez.core.data.source.network.response.people.PeopleResponse
import com.syauqi.watcheez.core.data.source.network.response.people_detail.PersonDetailResponse
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
): ApiHelper {
    override suspend fun getTrendingPeople(timeWindow: String) =
        apiService.getTrendingPeople(timeWindow)

    override suspend fun getPopularPeople(): BaseResponse<PeopleResponse> =
        apiService.getPopularPeople()

    override suspend fun getPersonById(id: Int): PersonDetailResponse = apiService.getPersonById(id)
    override suspend fun searchPeopleByQuery(query: String): BaseResponse<PeopleResponse> =
        apiService.searchPeopleByQuery(query)

    override suspend fun getTrendingMovies(): BaseResponse<MovieResponse> =
        apiService.getTrendingMovies()

    override suspend fun getMovieById(id: Int): MovieDetailResponse = apiService.getMovieById(id)
}