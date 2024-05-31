package com.syauqi.watcheez.presentation.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.syauqi.watcheez.domain.movie.usecase.MovieUseCase
import com.syauqi.watcheez.domain.people.usecase.PeopleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(peopleUseCase: PeopleUseCase, movieUseCase: MovieUseCase):ViewModel() {
    val popularArtist = peopleUseCase.getPopularPeople().asLiveData()
    val trendingArtist = peopleUseCase.getTrendingPeople().asLiveData()
    val trendingMovie = movieUseCase.getTrendingMovies(5).asLiveData()
}