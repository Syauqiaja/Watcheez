package com.syauqi.watcheez.presentation.features.artist_search

import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.syauqi.watcheez.core.data.source.network.response.ApiResponse
import com.syauqi.watcheez.databinding.FragmentSearchBinding
import com.syauqi.watcheez.domain.adapter.SearchArtistAdapter
import com.syauqi.watcheez.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate){
    private val viewModel : SearchViewModel by viewModels()
    private val searchArtistAdapter: SearchArtistAdapter = SearchArtistAdapter()

    override fun initView() {
        super.initView()

        binding.apply {
            rvResult.apply {
                adapter = searchArtistAdapter
                searchArtistAdapter.also {
                    adapter = it
                    it.onItemClick = {people ->
                        val action = SearchFragmentDirections.actionSearchFragmentToDetailArtistFragment(people)
                        findNavController().navigate(action)
                    }
                }
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            }

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let { viewModel.searchPeopleByQuery(it).observe(viewLifecycleOwner){result ->
                        when(result){
                            is ApiResponse.Success -> {
                                searchArtistAdapter.setData(result.data)
                            }
                            else -> {}
                        }
                    }}
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })
        }
    }
}