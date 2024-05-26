package com.syauqi.watcheez.core.data.source.network.datasource

import com.syauqi.watcheez.core.data.source.network.api.movie.ApiHelperMovie
import com.syauqi.watcheez.core.data.source.network.response.ApiResponse
import com.syauqi.watcheez.core.data.source.network.response.BaseResponse
import com.syauqi.watcheez.core.data.source.network.response.movie.CreditResponse
import com.syauqi.watcheez.core.data.source.network.response.movie.MovieDetailResponse
import com.syauqi.watcheez.core.data.source.network.response.movie.MovieResponse
import com.syauqi.watcheez.core.data.source.network.response.movie.WatchProviderResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDSMovie @Inject constructor(
    private val apiHelperMovie: ApiHelperMovie,
) {
    fun getTrendingMovies(): Flow<ApiResponse<BaseResponse<MovieResponse>>> {
        return object : RemoteDataSource<BaseResponse<MovieResponse>>(){
            override suspend fun fetchFromApi(): BaseResponse<MovieResponse> =
                apiHelperMovie.getTrendingMovies()
        }.asFlow()
    }
    fun getMovieById(id: Int): Flow<ApiResponse<MovieDetailResponse>> {
        return object : RemoteDataSource<MovieDetailResponse>(){
            override suspend fun fetchFromApi(): MovieDetailResponse {
                return apiHelperMovie.getMovieById(id)
            }
        }.asFlow()
    }

    fun getCreditsByMovieId(id: Int): Flow<ApiResponse<CreditResponse>> {
        return object : RemoteDataSource<CreditResponse>(){
            override suspend fun fetchFromApi(): CreditResponse = apiHelperMovie.getCreditsByMovieId(id)
            override fun emptyWhen(response: CreditResponse): Boolean {
                return response.casts.isEmpty()
            }
        }.asFlow()
    }
    fun getRelatedMovies(movieId: Int): Flow<ApiResponse<BaseResponse<MovieResponse>>> {
        return object : RemoteDataSource<BaseResponse<MovieResponse>>(){
            override suspend fun fetchFromApi(): BaseResponse<MovieResponse> = apiHelperMovie.getRelatedMovie(movieId)
            override fun emptyWhen(response: BaseResponse<MovieResponse>): Boolean {
                return response.totalResults == 0
            }
        }.asFlow()
    }
    fun getMovieProviders(movieId: Int): Flow<ApiResponse<WatchProviderResponse>>{
        return object : RemoteDataSource<WatchProviderResponse>(){
            override suspend fun fetchFromApi(): WatchProviderResponse {
                return apiHelperMovie.getMovieProviders(movieId)
            }

            override fun emptyWhen(response: WatchProviderResponse): Boolean {
                return response.results?.uS == null
            }
        }.asFlow()
    }
}