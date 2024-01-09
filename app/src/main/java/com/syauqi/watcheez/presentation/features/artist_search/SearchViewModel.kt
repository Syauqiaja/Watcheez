package com.syauqi.watcheez.presentation.features.artist_search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.syauqi.watcheez.core.data.source.network.response.ApiResponse
import com.syauqi.watcheez.domain.model.People
import com.syauqi.watcheez.domain.usecase.PeopleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val useCase: PeopleUseCase):ViewModel() {
    fun searchPeopleByQuery(query: String): LiveData<ApiResponse<List<People>>> {
        return useCase.searchPeopleByQuery(query).asLiveData()
    }
}