package com.syauqi.watcheez.presentation.features.artist_search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.syauqi.watcheez.core.data.Resource
import com.syauqi.watcheez.core.data.source.network.response.ApiResponse
import com.syauqi.watcheez.domain.people.model.People
import com.syauqi.watcheez.domain.people.usecase.PeopleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val useCase: PeopleUseCase):ViewModel() {
    fun searchPeopleByQuery(query: String): LiveData<Resource<List<People>>> {
        return useCase.searchPeopleByQuery(query).asLiveData()
    }
}