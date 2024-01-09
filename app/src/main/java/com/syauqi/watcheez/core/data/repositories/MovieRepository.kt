package com.syauqi.watcheez.core.data.repositories

import com.syauqi.watcheez.core.data.Resource
import com.syauqi.watcheez.core.data.source.local.LocalDataSource
import com.syauqi.watcheez.core.data.source.network.RemoteDataSource
import com.syauqi.watcheez.domain.movie.model.Movie
import com.syauqi.watcheez.domain.movie.model.MovieDetail
import com.syauqi.watcheez.domain.movie.repository.IMovieRepository
import com.syauqi.watcheez.utils.AppExecutors
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    localDataSource: LocalDataSource,
    remoteDataSource: RemoteDataSource,
    appExecutors: AppExecutors
): IMovieRepository {
    override fun getTrendingMovies(limit: Int): Flow<Resource<List<Movie>>> {
        TODO("Not yet implemented")
    }

    override fun getMovieById(id: Int): Flow<Resource<MovieDetail>> {
        TODO("Not yet implemented")
    }
}