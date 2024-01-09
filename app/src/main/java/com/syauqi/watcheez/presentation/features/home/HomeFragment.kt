package com.syauqi.watcheez.presentation.features.home

import android.opengl.Visibility
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.syauqi.watcheez.R
import com.syauqi.watcheez.core.data.Resource
import com.syauqi.watcheez.domain.people.adapter.PopularArtistAdapter
import com.syauqi.watcheez.presentation.base.BaseFragment
import com.syauqi.watcheez.databinding.FragmentHomeBinding
import com.syauqi.watcheez.domain.BaseAdapter
import com.syauqi.watcheez.domain.people.adapter.TrendingArtistAdapter
import com.syauqi.watcheez.domain.people.model.People
import com.syauqi.watcheez.utils.MovieGenres
import com.syauqi.watcheez.utils.asRemoteImagePath
import com.syauqi.watcheez.utils.enums.Gender
import com.syauqi.watcheez.utils.enums.ImageSize
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel : HomeViewModel by viewModels()
    private val popularActorAdapter: PopularArtistAdapter = PopularArtistAdapter()
    private val popularActressAdapter: PopularArtistAdapter = PopularArtistAdapter()
    private val trendingArtistAdapter: TrendingArtistAdapter = TrendingArtistAdapter()

    private var trendingMovieLoaded: Boolean = false

    override fun initView() {
        super.initView()
        binding.apply {
            if (!trendingMovieLoaded){
                shimmerPlaceholder.startShimmer()
                layoutHome.visibility = View.INVISIBLE
            }

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
        viewModel.apply {
            popularArtist.observe(requireActivity()){ result ->
                when(result){
                    is Resource.Success -> {
                        val actorList = result.data?.filter { it.gender == Gender.MALE.ordinal }
                        actorList?.let { popularActorAdapter.setData(it) }

                        val actressList = result.data?.filter { it.gender == Gender.FEMALE.ordinal }
                        actressList?.let { popularActressAdapter.setData(it) }
                        hideShimmer()
                    }
                    is Resource.Loading -> {
                    }
                    is Resource.Error -> {
                        Log.e("HomeFragment", result.message.toString())
                    }
                }
            }

            trendingArtist.observe(requireActivity()){ result ->
                when(result){
                    is Resource.Success -> {
                        result.data?.let { trendingArtistAdapter.setData(it) }
                        hideShimmer()
                    }
                    is Resource.Loading -> {
                    }
                    is Resource.Error -> {
                        Log.e("HomeFragment", result.message.toString())
                    }
                }
            }

            trendingMovie.observe(requireActivity()){result ->
                when(result){
                    is Resource.Success -> {
                        if(result.data != null){
                            trendingMovieLoaded = true
                            binding.cardRecommendedMovie.apply {
                                tvMovieTitle.text = result.data[0].title
                                tvGenreValue.text = MovieGenres.getMovieGenreById(result.data[0].genreIds[0])
                                val popularity = String.format("%.2f", result.data[0].popularity)
                                tvPopularity.text = getString(R.string.popularity_format, popularity)
                                tvRating.text =  String.format("%.2f", result.data[0].voteAverage)
                                Glide.with(requireContext())
                                    .load(result.data[0].backdropPath.asRemoteImagePath(ImageSize.ORIGINAL))
                                    .placeholder(shimmerDrawable)
                                    .into(ivBackdrop)
                            }
                            hideShimmer()
                        }
                    }
                    is Resource.Error -> {
                        Log.e("HomeFragment", result.message.toString())
                    }
                    is Resource.Loading -> {}
                }
            }
        }
    }

    private fun hideShimmer() {
        if (!checkBindingAvalability() || popularActorAdapter.itemCount <= 0 || trendingArtistAdapter.itemCount <= 0 || !trendingMovieLoaded) {
            return
        }

        binding.shimmerPlaceholder.apply {
            stopShimmer()
            visibility = View.GONE
        }
        binding.layoutHome.visibility = View.VISIBLE
    }

}