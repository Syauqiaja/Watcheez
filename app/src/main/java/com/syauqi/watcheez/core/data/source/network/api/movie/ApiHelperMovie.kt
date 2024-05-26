package com.syauqi.watcheez.core.data.source.network.api.movie

import com.syauqi.watcheez.core.data.source.network.response.movie.CreditResponse
import com.syauqi.watcheez.core.data.source.network.response.movie.MovieDetailResponse
import com.syauqi.watcheez.core.data.source.network.response.movie.MovieResponse
import com.syauqi.watcheez.core.data.source.network.response.BaseResponse
import com.syauqi.watcheez.core.data.source.network.response.movie.WatchProviderResponse

interface ApiHelperMovie {

    suspend fun getTrendingMovies(): BaseResponse<MovieResponse>
    suspend fun getMovieById(id: Int): MovieDetailResponse
    suspend fun getCreditsByMovieId(id:Int): CreditResponse
    suspend fun getRelatedMovie(movieId: Int): BaseResponse<MovieResponse>
    suspend fun getMovieProviders(movieId: Int): WatchProviderResponse
}