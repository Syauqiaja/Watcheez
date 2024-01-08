package com.syauqi.watcheez.presentation.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.syauqi.watcheez.domain.repository.PeopleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: PeopleRepository):ViewModel() {
    val trendingArtist = repository.getTrendingPeople().asLiveData()
    val popularArtist = repository.getPopularPeople().asLiveData()
}