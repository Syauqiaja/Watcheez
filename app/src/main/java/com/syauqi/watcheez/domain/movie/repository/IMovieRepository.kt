package com.syauqi.watcheez.domain.movie.repository

import com.syauqi.watcheez.core.data.Resource
import com.syauqi.watcheez.domain.movie.model.Movie
import com.syauqi.watcheez.domain.movie.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getTrendingMovies(limit: Int): Flow<Resource<List<Movie>>>
    fun getMovieById(id: Int): Flow<Resource<MovieDetail>>
}