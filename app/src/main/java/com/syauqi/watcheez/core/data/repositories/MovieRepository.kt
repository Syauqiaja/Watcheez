package com.syauqi.watcheez.core.data.repositories

import com.syauqi.watcheez.core.data.NetworkBoundResource
import com.syauqi.watcheez.core.data.Resource
import com.syauqi.watcheez.core.data.source.local.LocalDataSource
import com.syauqi.watcheez.core.data.source.network.RemoteDataSource
import com.syauqi.watcheez.core.data.source.network.api.ApiService
import com.syauqi.watcheez.core.data.source.network.response.ApiResponse
import com.syauqi.watcheez.core.data.source.network.response.movie.MovieResponse
import com.syauqi.watcheez.core.data.source.network.response.people.BaseResponse
import com.syauqi.watcheez.domain.movie.model.Movie
import com.syauqi.watcheez.domain.movie.model.MovieDetail
import com.syauqi.watcheez.domain.movie.repository.IMovieRepository
import com.syauqi.watcheez.utils.AppExecutors
import com.syauqi.watcheez.utils.DataMapper.toListMovie
import com.syauqi.watcheez.utils.DataMapper.toMovieDetail
import com.syauqi.watcheez.utils.DataMapper.toPeopleArrayList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val appExecutors: AppExecutors
): IMovieRepository {
    override fun getTrendingMovies(limit: Int): Flow<Resource<List<Movie>>> {
        return flow<Resource<List<Movie>>> {
            emit(Resource.Loading())
            val response = remoteDataSource.getTrendingMovies()
            response.collect { result ->
                when (result) {
                    is ApiResponse.Success -> {
                        val data = result.data.results.toListMovie()
                        emit(Resource.Success(data.subList(0,limit)))
                    }

                    is ApiResponse.Empty -> {}

                    is ApiResponse.Error -> {
                        emit(Resource.Error(result.errorMessage))
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getMovieById(id: Int): Flow<Resource<MovieDetail>> {
        return flow<Resource<MovieDetail>> {
            emit(Resource.Loading())
            val response = remoteDataSource.getMovieById(id)
            response.collect { result ->
                when (result) {
                    is ApiResponse.Success -> {
                        val data = result.data
                        emit(Resource.Success(data.toMovieDetail()))
                    }

                    is ApiResponse.Empty -> {
                        emit(Resource.Error("Movie Not Found"))}

                    is ApiResponse.Error -> {
                        emit(Resource.Error(result.errorMessage))
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
    }
}