package com.syauqi.watcheez.presentation.features.artist_search

import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.syauqi.watcheez.core.data.Resource
import com.syauqi.watcheez.databinding.FragmentSearchBinding
import com.syauqi.watcheez.presentation.features.artist_search.adapter.SearchArtistAdapter
import com.syauqi.watcheez.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate){
    private val viewModel : SearchViewModel by viewModels()
    private val searchArtistAdapter: SearchArtistAdapter = SearchArtistAdapter()

    override fun initView() {
        super.initView()

        searchArtistAdapter.onItemClick = {
            val action = SearchFragmentDirections.actionSearchFragmentToDetailArtistFragment(it)
            findNavController().navigate(action)
        }

        binding.apply {
            emptyLayout.visibility = View.GONE
            progressBar.visibility = View.GONE

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
                    val q = query ?: ""
                    viewModel.searchPeopleByQuery(q).observe(viewLifecycleOwner){result ->
                        when(result){
                            is Resource.Success -> {
                                showLoading(false)
                                if(result.data.isNullOrEmpty()){
                                    showEmpty(true)
                                }else{
                                    showEmpty(false)
                                    searchArtistAdapter.setData(result.data)
                                }
                            }
                            is Resource.Loading -> {
                                showLoading(true)
                            }
                            is Resource.Error -> {
                                showLoading(false)
                                showEmpty(true)
                                Toast.makeText(requireContext(), "Error occured", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })
        }
    }

    fun showLoading(value: Boolean){
        binding.progressBar.visibility = if(value){
            binding.emptyLayout.visibility = View.GONE
            View.VISIBLE
        }else{
            View.GONE
        }
    }

    fun showEmpty(value: Boolean){
        binding.rvResult.visibility = if(value){
            View.GONE
        }else{
            View.VISIBLE
        }

        binding.emptyLayout.visibility = if(value){
            View.VISIBLE
        }else{
            View.GONE
        }
    }
}