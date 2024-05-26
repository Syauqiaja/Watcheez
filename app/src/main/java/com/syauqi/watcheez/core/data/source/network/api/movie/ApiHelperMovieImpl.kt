package com.syauqi.watcheez.core.data.source.network.api.movie

import com.syauqi.watcheez.core.data.source.network.api.ApiService
import com.syauqi.watcheez.core.data.source.network.response.movie.CreditResponse
import com.syauqi.watcheez.core.data.source.network.response.movie.MovieDetailResponse
import com.syauqi.watcheez.core.data.source.network.response.movie.MovieResponse
import com.syauqi.watcheez.core.data.source.network.response.BaseResponse
import com.syauqi.watcheez.core.data.source.network.response.movie.WatchProviderResponse
import javax.inject.Inject

class ApiHelperMovieImpl @Inject constructor(
    private val apiService: ApiService
): ApiHelperMovie {

    override suspend fun getTrendingMovies(): BaseResponse<MovieResponse> =
        apiService.getTrendingMovies()

    override suspend fun getMovieById(id: Int): MovieDetailResponse = apiService.getMovieById(id)
    override suspend fun getCreditsByMovieId(id: Int): CreditResponse {
        return apiService.getCreditsByMovieId(id)
    }

    override suspend fun getRelatedMovie(movieId: Int): BaseResponse<MovieResponse> {
        return apiService.getRelatedMovie(movieId)
    }

    override suspend fun getMovieProviders(movieId: Int): WatchProviderResponse {
        return apiService.getMovieProviders(movieId)
    }
}