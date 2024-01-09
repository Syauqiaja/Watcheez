package com.syauqi.watcheez.presentation.features.artist_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.syauqi.watcheez.domain.people.usecase.PeopleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailArtistViewModel @Inject constructor(private val repository: PeopleUseCase): ViewModel(){
    fun peopleDetail(id: Int) = repository.getPeopleById(id).asLiveData()
}