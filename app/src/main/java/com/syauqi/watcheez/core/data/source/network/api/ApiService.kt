package com.syauqi.watcheez.core.data.source.network.api

import com.syauqi.watcheez.core.data.source.network.response.movie.CreditResponse
import com.syauqi.watcheez.core.data.source.network.response.movie.MovieDetailResponse
import com.syauqi.watcheez.core.data.source.network.response.movie.MovieResponse
import com.syauqi.watcheez.core.data.source.network.response.BaseResponse
import com.syauqi.watcheez.core.data.source.network.response.movie.WatchProviderResponse
import com.syauqi.watcheez.core.data.source.network.response.people.PeopleResponse
import com.syauqi.watcheez.core.data.source.network.response.people.people_detail.PersonDetailResponse
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

    @GET("search/person?query=ang&include_adult=false&language=en-US&page=1")
    suspend fun searchPeopleByQuery(
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey : String = Constants.API_KEY,
    ): BaseResponse<PeopleResponse>

    @GET("trending/movie/{time_window}")
    suspend fun getTrendingMovies(
        @Path("time_window") timeWindow: String = "day",
        @Query("api_key") apiKey : String = Constants.API_KEY
    ): BaseResponse<MovieResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieById(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey : String = Constants.API_KEY
    ): MovieDetailResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getCreditsByMovieId(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey : String = Constants.API_KEY
    ): CreditResponse

    @GET("movie/{movie_id}/similar")
    suspend fun getRelatedMovie(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey : String = Constants.API_KEY
    ): BaseResponse<MovieResponse>

    @GET("movie/{movie_id}/watch/providers")
    suspend fun getMovieProviders(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey : String = Constants.API_KEY
    ): WatchProviderResponse
}