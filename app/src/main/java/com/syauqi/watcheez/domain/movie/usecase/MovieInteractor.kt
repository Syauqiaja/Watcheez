package com.syauqi.watcheez.domain.movie.usecase

import com.syauqi.watcheez.core.data.Resource
import com.syauqi.watcheez.domain.movie.model.Movie
import com.syauqi.watcheez.domain.movie.model.MovieDetail
import com.syauqi.watcheez.domain.movie.model.MovieProviders
import com.syauqi.watcheez.domain.movie.repository.IMovieRepository
import com.syauqi.watcheez.domain.people.model.People
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(private val repository: IMovieRepository): MovieUseCase {
    override fun getTrendingMovies(limit: Int): Flow<Resource<List<Movie>>> {
        return repository.getTrendingMovies(limit)
    }

    override fun getMovieById(id: Int): Flow<Resource<MovieDetail>> {
        return repository.getMovieById(id)
    }

    override fun getCreditsByMovieId(id: Int): Flow<Resource<List<People>>> {
        return repository.getCreditsByMovieId(id)
    }

    override fun getRelatedMovie(movieId: Int): Flow<Resource<List<Movie>>> {
        return repository.getRelatedMovie(movieId)
    }

    override fun getMovieProviders(movieId: Int): Flow<Resource<MovieProviders>> {
        return repository.getWatchProviders(movieId)
    }
}