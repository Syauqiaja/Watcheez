package com.syauqi.watcheez.presentation.features.artist_favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.syauqi.watcheez.R
import com.syauqi.watcheez.core.data.Resource
import com.syauqi.watcheez.core.data.source.network.response.ApiResponse
import com.syauqi.watcheez.databinding.FragmentFavoriteArtistBinding
import com.syauqi.watcheez.domain.people.adapter.SearchArtistAdapter
import com.syauqi.watcheez.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteArtistFragment : BaseFragment<FragmentFavoriteArtistBinding>(
    FragmentFavoriteArtistBinding::inflate
) {
    private val viewModel: FavoriteArtistViewModel by viewModels()
    private val searchArtistAdapter: SearchArtistAdapter = SearchArtistAdapter()

    override fun initView() {
        super.initView()
        searchArtistAdapter.onItemClick = {
            val action = FavoriteArtistFragmentDirections.actionFavoriteFragmentToDetailArtistFragment2(it)
            findNavController().navigate(action)
        }
        binding.rvFavoriteArtist.apply {
            adapter = searchArtistAdapter
            addItemDecoration(
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            )
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.favoriteArtist.observe(viewLifecycleOwner){result ->
            when(result){
                is Resource.Success -> {
                    showLoading(false)
                    searchArtistAdapter.setData(result.data!!)
                    binding.emptyLayout.visibility = View.GONE
                }
                is Resource.Error -> {
                    showLoading(false)
                    binding.emptyLayout.visibility = View.VISIBLE
                }
                is Resource.Loading -> {
                    showLoading(true)
                }
            }
        }
    }

    fun showLoading(value:Boolean){
        binding.progressBar.visibility = if(value){
            binding.emptyLayout.visibility = View.GONE
            View.VISIBLE
        }else{
            View.GONE
        }
    }
}