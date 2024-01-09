package com.syauqi.watcheez.presentation.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.syauqi.watcheez.domain.usecase.PeopleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(repository: PeopleUseCase):ViewModel() {
    val popularArtist = repository.getPopularPeople().asLiveData()
    val trendingArtist = repository.getTrendingPeople().asLiveData()
}