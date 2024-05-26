package com.syauqi.watcheez.domain.movie.usecase

import com.syauqi.watcheez.core.data.Resource
import com.syauqi.watcheez.domain.movie.model.Movie
import com.syauqi.watcheez.domain.movie.model.MovieDetail
import com.syauqi.watcheez.domain.movie.model.MovieProviders
import com.syauqi.watcheez.domain.people.model.People
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getTrendingMovies(limit: Int): Flow<Resource<List<Movie>>>
    fun getMovieById(id: Int): Flow<Resource<MovieDetail>>
    fun getCreditsByMovieId(id: Int): Flow<Resource<List<People>>>
    fun getRelatedMovie(movieId: Int): Flow<Resource<List<Movie>>>
    fun getMovieProviders(movieId: Int): Flow<Resource<MovieProviders>>
}