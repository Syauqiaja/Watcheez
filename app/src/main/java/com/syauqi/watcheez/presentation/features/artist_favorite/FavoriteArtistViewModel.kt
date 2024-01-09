package com.syauqi.watcheez.presentation.features.artist_favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.syauqi.watcheez.core.data.Resource
import com.syauqi.watcheez.domain.people.model.People
import com.syauqi.watcheez.domain.people.usecase.PeopleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteArtistViewModel @Inject constructor(private val peopleUseCase: PeopleUseCase): ViewModel() {
    var favoriteArtist : LiveData<Resource<List<People>>> = peopleUseCase.getFavoritePeople().asLiveData()
}