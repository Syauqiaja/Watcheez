package com.syauqi.watcheez.core.data.repositories

import com.syauqi.watcheez.core.data.Resource
import com.syauqi.watcheez.core.data.source.local.LocalDSPeople
import com.syauqi.watcheez.core.data.source.network.datasource.RemoteDSMovie
import com.syauqi.watcheez.core.data.source.network.response.ApiResponse
import com.syauqi.watcheez.domain.movie.model.Movie
import com.syauqi.watcheez.domain.movie.model.MovieDetail
import com.syauqi.watcheez.domain.movie.model.MovieProviders
import com.syauqi.watcheez.domain.movie.repository.IMovieRepository
import com.syauqi.watcheez.domain.people.model.People
import com.syauqi.watcheez.utils.DataMapper.toListMovie
import com.syauqi.watcheez.utils.DataMapper.toMovieDetail
import com.syauqi.watcheez.utils.DataMapper.toPeopleArrayList
import com.syauqi.watcheez.utils.DataMapper.toPeopleEntityArrayList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val localDSPeople: LocalDSPeople,
    private val remoteDSMovie: RemoteDSMovie
): IMovieRepository {
    override fun getTrendingMovies(limit: Int): Flow<Resource<List<Movie>>> {
        return flow<Resource<List<Movie>>> {
            emit(Resource.Loading())
            val response = remoteDSMovie.getTrendingMovies()
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
            val response = remoteDSMovie.getMovieById(id)
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

    override fun getCreditsByMovieId(id: Int): Flow<Resource<List<People>>> = flow {
        emit(Resource.Loading())
        val response = remoteDSMovie.getCreditsByMovieId(id)
        response.collect{result ->
            when (result) {
                is ApiResponse.Success -> {
                    val data = result.data
                    val entityList = data.casts.toPeopleEntityArrayList()
                    localDSPeople.insertAlPeople(entityList)
                    emit(Resource.Success(entityList.toPeopleArrayList()))
                }
                is ApiResponse.Empty -> {
                    emit(Resource.Error("There are no registered credits"))}

                is ApiResponse.Error -> {
                    emit(Resource.Error(result.errorMessage))
                }
            }
        }
    }

    override fun getRelatedMovie(movieId: Int): Flow<Resource<List<Movie>>> = flow{
        emit(Resource.Loading())
        val response = remoteDSMovie.getRelatedMovies(movieId)
        response.collect{result ->
            when (result) {
                is ApiResponse.Success -> {
                    val data = result.data
                    emit(Resource.Success(data.results.toListMovie()))
                }
                is ApiResponse.Empty -> {
                    emit(Resource.Error("There are no related movies"))}

                is ApiResponse.Error -> {
                    emit(Resource.Error(result.errorMessage))
                }
            }
        }
    }

    override fun getWatchProviders(movieId: Int): Flow<Resource<MovieProviders>> = flow{
        emit(Resource.Loading())
        val response = remoteDSMovie.getMovieProviders(movieId)
        response.collect{result ->
            when(result){
                is ApiResponse.Success -> {
                    val data = result.data
                    emit(Resource.Success(data.toMovieProviderDomain(5)))
                }
                is ApiResponse.Empty -> {
                    emit(Resource.Error("There are no provider"))}

                is ApiResponse.Error -> {
                    emit(Resource.Error(result.errorMessage))
                }
            }
        }
    }
}