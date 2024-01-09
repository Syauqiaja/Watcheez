package com.syauqi.watcheez.presentation.features.artist_detail

import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.viewModels
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.syauqi.watcheez.R
import com.syauqi.watcheez.core.data.Resource
import com.syauqi.watcheez.core.data.source.network.response.ApiResponse
import com.syauqi.watcheez.databinding.FragmentDetailArtistBinding
import com.syauqi.watcheez.domain.people.adapter.ArtistPhotoAdapter
import com.syauqi.watcheez.domain.people.adapter.FilmoghraphyAdapter
import com.syauqi.watcheez.presentation.base.BaseFragment
import com.syauqi.watcheez.utils.asRemoteImagePath
import com.syauqi.watcheez.utils.enums.ImageSize
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailArtistFragment : BaseFragment<FragmentDetailArtistBinding>(FragmentDetailArtistBinding::inflate){
    private val args : DetailArtistFragmentArgs by navArgs()
    private val viewModel : DetailArtistViewModel by viewModels()
    private val filmoghraphyAdapter = FilmoghraphyAdapter()
    private val artistPhotoAdapter = ArtistPhotoAdapter()

    override fun initView() {
        super.initView()
        val people = args.people
        binding.apply {
            tvActorName.text = people.name
            tvArtistPopularity.text = String.format("%.2f", people.popularity)
            Glide.with(this@DetailArtistFragment)
                .load(people.photoUrl.asRemoteImagePath(ImageSize.W_300))
                .placeholder(shimmerDrawable)
                .into(ivActorProfileHead)
            rvActorPhotos.adapter = artistPhotoAdapter
            rvFilmography.adapter = filmoghraphyAdapter
            videoPlayer.player = setupVideoPlayer()
        }

        viewModel.peopleDetail(people.id).observe(viewLifecycleOwner){result ->
            when(result){
                is Resource.Success -> {
                    binding.apply {
                        result.data?.also {
                            tvActorStoryDesc.text = it.biography
                            tvBornValuePlace.text = it.placeOfBirth
                            tvBornValueDate.text = it.birthday
                            tvContactValue.text = getString(R.string.imdb_id, it.externalIds.imdbId)

                            if(it.images.size > 8) artistPhotoAdapter.setData(it.images.subList(0,8))
                            else artistPhotoAdapter.setData(it.images)

                            it.movieCredits?.let { movieCredits ->
                                if(movieCredits.size > 8) filmoghraphyAdapter.setData(movieCredits.subList(0,8))
                                else filmoghraphyAdapter.setData(movieCredits)
                            }
                        }
                    }
                }
                else -> {}
            }
        }
    }

    private fun setupVideoPlayer() : Player {
        val videoItem = MediaItem.fromUri("https://storage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4")
         return ExoPlayer.Builder(requireContext()).build().also { exoPlayer ->
            exoPlayer.setMediaItem(videoItem)
            exoPlayer.prepare()
        }
    }

    fun hideSystemUI(){
        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, false)
        WindowInsetsControllerCompat(requireActivity().window, binding.videoPlayer).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

}