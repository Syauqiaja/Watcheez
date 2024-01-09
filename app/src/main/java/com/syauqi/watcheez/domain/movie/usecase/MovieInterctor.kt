package com.syauqi.watcheez.domain.movie.usecase

import com.syauqi.watcheez.core.data.Resource
import com.syauqi.watcheez.domain.movie.model.Movie
import com.syauqi.watcheez.domain.movie.model.MovieDetail
import com.syauqi.watcheez.domain.movie.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInterctor @Inject constructor(repository: IMovieRepository): MovieUseCase {
    override fun getTrendingMovies(limit: Int): Flow<Resource<List<Movie>>> {
        TODO("Not yet implemented")
    }

    override fun getMovieById(id: Int): Flow<Resource<MovieDetail>> {
        TODO("Not yet implemented")
    }
}