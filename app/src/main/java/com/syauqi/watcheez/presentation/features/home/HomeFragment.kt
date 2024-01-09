package com.syauqi.watcheez.presentation.features.home

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.syauqi.watcheez.domain.adapter.PopularArtistAdapter
import com.syauqi.watcheez.presentation.base.BaseFragment
import com.syauqi.watcheez.core.data.source.network.response.ApiResponse
import com.syauqi.watcheez.databinding.FragmentHomeBinding
import com.syauqi.watcheez.domain.adapter.TrendingArtistAdapter
import com.syauqi.watcheez.domain.model.People
import com.syauqi.watcheez.utils.enums.Gender
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel : HomeViewModel by viewModels()
    private val popularActorAdapter: PopularArtistAdapter = PopularArtistAdapter()
    private val popularActressAdapter: PopularArtistAdapter = PopularArtistAdapter()
    private val trendingArtistAdapter: TrendingArtistAdapter = TrendingArtistAdapter()

    override fun initView() {
        super.initView()
        binding.apply {
            rvPopularActors.adapter = popularActorAdapter
            popularActorAdapter.onItemClick = {navigateToPeople(it)}

            rvTrendingArtist.adapter = trendingArtistAdapter
            trendingArtistAdapter.onItemClick = {navigateToPeople(it)}

            rvPopularActress.adapter = popularActressAdapter
            popularActressAdapter.onItemClick = {navigateToPeople(it)}
        }
    }

    private fun navigateToPeople(people: People){
        val action = HomeFragmentDirections.actionItemActorToActorDetailFragment(people)
        findNavController().navigate(action)
    }

    override fun observeViewModel() {
        super.observeViewModel()
        viewModel.popularArtist.observe(viewLifecycleOwner){ result ->
            when(result){
                is ApiResponse.Success -> {
                    val actorList = result.data.filter { it.gender == Gender.MALE.ordinal }
                    popularActorAdapter.setData(actorList)

                    val actressList = result.data.filter { it.gender == Gender.FEMALE.ordinal }
                    popularActressAdapter.setData(actressList)
                }
                else -> {}
            }
        }
        viewModel.trendingArtist.observe(viewLifecycleOwner){ result ->
            when(result){
                is ApiResponse.Success -> {
                    trendingArtistAdapter.setData(result.data)
                }
                else -> {}
            }
        }
    }

}