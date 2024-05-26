package com.syauqi.watcheez.presentation.features.movie_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.syauqi.watcheez.core.data.Resource
import com.syauqi.watcheez.domain.movie.model.Movie
import com.syauqi.watcheez.domain.movie.model.MovieDetail
import com.syauqi.watcheez.domain.movie.model.MovieProviders
import com.syauqi.watcheez.domain.movie.usecase.MovieUseCase
import com.syauqi.watcheez.domain.people.model.People
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
):ViewModel() {
    val movie : MutableLiveData<Resource<MovieDetail>> = MutableLiveData()
    val credits: MutableLiveData<Resource<List<People>>> = MutableLiveData()
    val relatedMovie: MutableLiveData<Resource<List<Movie>>> = MutableLiveData()

    init {
        movie.value = Resource.Loading()
        credits.value = Resource.Loading()
        relatedMovie.value = Resource.Loading()
    }
    fun getMovie(id: Int) {
        viewModelScope.launch {
            movieUseCase.getMovieById(id).collectLatest {
                movie.value = it
            }
        }
        viewModelScope.launch {
            movieUseCase.getCreditsByMovieId(id).collectLatest {
                credits.value = it
            }
        }
        viewModelScope.launch {
            movieUseCase.getRelatedMovie(id).collectLatest {
                relatedMovie.value = it
            }
        }
    }

    fun getMovieProvider(id: Int):Flow<Resource<MovieProviders>> = movieUseCase.getMovieProviders(id)
}