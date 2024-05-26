package com.syauqi.watcheez.presentation.features.movie_detail

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.appbar.AppBarLayout
import com.syauqi.watcheez.R
import com.syauqi.watcheez.core.data.Resource
import com.syauqi.watcheez.databinding.FragmentMovieDetailBinding
import com.syauqi.watcheez.domain.movie.model.Movie
import com.syauqi.watcheez.domain.movie.model.MovieDetail
import com.syauqi.watcheez.domain.people.model.People
import com.syauqi.watcheez.presentation.base.BaseFragment
import com.syauqi.watcheez.presentation.features.home.adapter.PopularArtistAdapter
import com.syauqi.watcheez.presentation.features.movie_detail.adapter.MovieListAdapter
import com.syauqi.watcheez.utils.MarginItemDecoration
import com.syauqi.watcheez.utils.blurAtRuntime
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.abs

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding>(
    FragmentMovieDetailBinding::inflate
) {
    private val viewModel : MovieDetailViewModel by viewModels()
    private val movieId : Int by lazy {
        MovieDetailFragmentArgs.fromBundle(requireArguments()).movieId
    }

    override fun initView() {
        binding.apply {
            appBar.addOnOffsetChangedListener(appbarScrollOffsetListener)
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun observeViewModel() {
        viewModel.movie.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> {}
                is Resource.Success -> {
                    setupMovieDetailView(it.data!!)
                }
                is Resource.Error -> {}
            }
        }
        viewModel.credits.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> {}
                is Resource.Success -> {
                    setupCredits(it.data!!)
                }
                is Resource.Error -> {}
            }
        }
        viewModel.relatedMovie.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> {}
                is Resource.Success -> {
                    setupRelatedMovies(it.data!!)
                }
                is Resource.Error -> {}
            }
        }

        viewModel.getMovie(movieId)
    }

    @SuppressLint("StringFormatMatches")
    private fun setupMovieDetailView(movie:MovieDetail){
        binding.apply {
            collapsingToolbar.title = movie.title
            toolbar.title = movie.title
            tvMovieTitle.text = movie.title
            tvPopularity.text = String.format("%.2f", movie.popularity)
            tvSummary.text = movie.overview
            tvTagline.text = movie.tagline
            tvDetailHead.text = getString(R.string.detail_head_movie,
                movie.releaseDate.year,
                movie.genres.seperateByComma(),
                movie.duration.toDurationFormat()
            )
            tvProductionCompany.text =
                getString(R.string.production_company_s, movie.productionCompanies[0])
            tvDirector.text = getString(R.string.director_s, "N/A")

            btnWatch.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.getMovieProvider(movieId).collectLatest { result ->
                        when(result){
                            is Resource.Success -> {
                                if(result.data != null){
                                    val dialogFragment = WatchOnDialog(result.data)
                                    dialogFragment.show(childFragmentManager, WatchOnDialog.TAG)
                                }else{
                                    Toast.makeText(requireContext(), "Movie is not ready yet", Toast.LENGTH_SHORT).show()
                                }
                            }
                            else -> Toast.makeText(requireContext(), "Movie is not ready yet", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            Glide.with(requireContext())
                .asBitmap()
                .placeholder(shimmerDrawable)
                .load(movie.backdropUrl)
                .into(object : CustomTarget<Bitmap>(){
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        binding.ivBackdrop.setImageBitmap(resource.blurAtRuntime(requireContext()))
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {}
                })
            Glide.with(requireContext())
                .load(movie.posterUrl)
                .placeholder(shimmerDrawable)
                .into(binding.ivPoster)
        }
    }

    private fun List<String>.seperateByComma(): String{
        var result = ""
        forEach{
            result += "$it, "
        }
        return result.removeSuffix(", ")
    }
    private fun Int.toDurationFormat(): String{
        return if(this > 0){
            var result = ""
            val hours = this/60
            val minutes = this%60;
            if(hours > 0){
                result += "${hours}h"
            }
            result += "${minutes}m"
            result
        }else{
            "N/A"
        }
    }

    private fun setupCredits(listPeople: List<People>){
        binding.rvCredits.apply {
            adapter = PopularArtistAdapter().also {
                it.setData(listPeople)
                it.onItemClick = {
                    val action = MovieDetailFragmentDirections.actionMovieDetailFragmentToDetailArtistFragment(it)
                    findNavController().navigate(action)
                }
            }
            layoutManager = LinearLayoutManager(requireContext()).also {
                it.orientation = LinearLayoutManager.HORIZONTAL
            }
        }
    }
    private fun setupRelatedMovies(listMovies: List<Movie>){
        binding.rvRelatedMovies.apply {
            adapter = MovieListAdapter().also {
                it.setData(listMovies)
                it.onItemClick = {movie ->
                    val action = MovieDetailFragmentDirections.actionMovieDetailFragmentSelf(movie.id)
                    findNavController().navigate(action)
                }
            }
            layoutManager = LinearLayoutManager(requireContext()).also {
                it.orientation = LinearLayoutManager.VERTICAL
            }
            addItemDecoration(MarginItemDecoration(requireContext(), 8))
        }
    }

    private val appbarScrollOffsetListener = AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
        binding.appbarForeground.alpha = abs(verticalOffset.toFloat() / appBarLayout.totalScrollRange.toFloat())
    }
}