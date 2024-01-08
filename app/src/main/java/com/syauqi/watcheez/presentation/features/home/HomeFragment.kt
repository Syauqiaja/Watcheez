package com.syauqi.watcheez.presentation.features.home

import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.syauqi.watcheez.domain.adapter.TrendingArtistAdapter
import com.syauqi.watcheez.presentation.base.BaseFragment
import com.syauqi.watcheez.core.data.source.network.response.ApiResponse
import com.syauqi.watcheez.databinding.FragmentHomeBinding
import com.syauqi.watcheez.domain.adapter.TopArtistAdapter
import com.syauqi.watcheez.domain.models.People
import com.syauqi.watcheez.utils.enums.Gender
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel : HomeViewModel by viewModels()
    private val trendingActorAdapter: TrendingArtistAdapter = TrendingArtistAdapter()
    private val trendingActressAdapter: TrendingArtistAdapter = TrendingArtistAdapter()
    private val topArtistAdapter: TopArtistAdapter = TopArtistAdapter()

    override fun initView() {
        super.initView()
        binding.apply {
            rvTrendingActors.adapter = trendingActorAdapter
            trendingActorAdapter.onItemClick = {navigateToPeople(it)}

            rvTopArtist.adapter = topArtistAdapter
            topArtistAdapter.onItemClick = {navigateToPeople(it)}

            rvTrendingActress.adapter = trendingActressAdapter
            trendingActressAdapter.onItemClick = {navigateToPeople(it)}
        }
    }

    private fun navigateToPeople(people: People){
        val action = HomeFragmentDirections.actionItemActorToActorDetailFragment(people)
        findNavController().navigate(action)
    }

    override fun observeViewModel() {
        super.observeViewModel()
        viewModel.trendingArtist.observe(viewLifecycleOwner){result ->
            when(result){
                is ApiResponse.Success -> {
                    val actorList = result.data.filter { it.gender == Gender.MALE.ordinal }
                    trendingActorAdapter.setData(actorList)

                    val actressList = result.data.filter { it.gender == Gender.FEMALE.ordinal }
                    trendingActressAdapter.setData(actressList)
                }
                else -> {}
            }
        }
        viewModel.popularArtist.observe(viewLifecycleOwner){result ->
            when(result){
                is ApiResponse.Success -> {
                    topArtistAdapter.setData(result.data)
                }
                else -> {}
            }
        }
    }
}