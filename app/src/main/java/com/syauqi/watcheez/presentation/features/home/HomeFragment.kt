package com.syauqi.watcheez.presentation.features.home

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.carousel.CarouselSnapHelper
import com.syauqi.watcheez.R
import com.syauqi.watcheez.core.data.Resource
import com.syauqi.watcheez.presentation.features.home.adapter.PopularArtistAdapter
import com.syauqi.watcheez.presentation.base.BaseFragment
import com.syauqi.watcheez.databinding.FragmentHomeBinding
import com.syauqi.watcheez.databinding.ItemMovieLandscapeBinding
import com.syauqi.watcheez.domain.movie.model.Movie
import com.syauqi.watcheez.presentation.features.home.adapter.TrendingArtistAdapter
import com.syauqi.watcheez.domain.people.model.People
import com.syauqi.watcheez.presentation.features.home.adapter.MovieCarouselAdapter
import com.syauqi.watcheez.presentation.features.movie_detail.adapter.MovieListAdapter
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
            popularArtist.observe(viewLifecycleOwner){ result ->
                when(result){
                    is Resource.Success -> {
                        val actorList = result.data?.filter { it.gender == Gender.MALE.ordinal }
                        actorList?.let {
                            if(it.size > 5) popularActorAdapter.setData(it.subList(0,5))
                            else popularActorAdapter.setData(it)
                        }

                        val actressList = result.data?.filter { it.gender == Gender.FEMALE.ordinal }
                        actressList?.let {
                            if(it.size > 5)  popularActressAdapter.setData(it.subList(0,5))
                            else popularActressAdapter.setData(it)
                        }
                        hideShimmer()
                    }
                    is Resource.Loading -> {
                    }
                    is Resource.Error -> {
                        Log.e("HomeFragment", result.message.toString())
                    }
                }
            }

            trendingArtist.observe(viewLifecycleOwner){ result ->
                when(result){
                    is Resource.Success -> {
                        result.data?.let {
                            if(it.size > 4) trendingArtistAdapter.setData(it.subList(0,4))
                            else trendingArtistAdapter.setData(it)
                        }
                        hideShimmer()
                    }
                    is Resource.Loading -> {
                    }
                    is Resource.Error -> {
                        Log.e("HomeFragment", result.message.toString())
                    }
                }
            }

            trendingMovie.observe(viewLifecycleOwner){result ->
                when(result){
                    is Resource.Success -> {
                        if(result.data != null){
                            trendingMovieLoaded = true
                            binding.cardRecommendedMovie1.bind(result.data[0])
                            binding.cardRecommendedMovie1.ivBackdrop.setOnClickListener {
                                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(result.data[0].id))
                            }
                            binding.cardRecommendedMovie2.bind(result.data[1])
                            binding.cardRecommendedMovie2.ivBackdrop.setOnClickListener {
                                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(result.data[1].id))
                            }
                            binding.cardRecommendedMovie3.bind(result.data[2])
                            binding.cardRecommendedMovie3.ivBackdrop.setOnClickListener {
                                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(result.data[2].id))
                            }
                            binding.cardRecommendedMovie4.bind(result.data[3])
                            binding.cardRecommendedMovie4.ivBackdrop.setOnClickListener {
                                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(result.data[3].id))
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

    private fun ItemMovieLandscapeBinding.bind(movie: Movie){
        tvMovieTitle.text = movie.title
        tvGenreValue.text = MovieGenres.getMovieGenreById(movie.genreIds[0])
        val popularity = String.format("%.2f", movie.popularity)
        tvPopularity.text = getString(R.string.popularity_format, popularity)
        tvRating.text =  String.format("%.2f", movie.voteAverage)
        Glide.with(requireContext())
            .load(movie.backdropPath.asRemoteImagePath(ImageSize.ORIGINAL))
            .placeholder(shimmerDrawable)
            .into(ivBackdrop)
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