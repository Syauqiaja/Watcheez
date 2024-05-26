package com.syauqi.watcheez.presentation.features.movie_detail.adapter

import android.view.LayoutInflater
import android.view.View
import com.bumptech.glide.Glide
import com.syauqi.watcheez.R
import com.syauqi.watcheez.databinding.ItemMovieLandscapeBinding
import com.syauqi.watcheez.domain.BaseAdapter
import com.syauqi.watcheez.domain.movie.model.Movie
import com.syauqi.watcheez.utils.MovieGenres
import com.syauqi.watcheez.utils.asRemoteImagePath
import com.syauqi.watcheez.utils.enums.ImageSize

class MovieListAdapter:BaseAdapter<ItemMovieLandscapeBinding, Movie>(
    inflateMethod = {parent, _ ->
        ItemMovieLandscapeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }
) {
    override fun bindView(movie: Movie, binding: ItemMovieLandscapeBinding, itemView: View) {
        binding.apply {
            tvMovieTitle.text = movie.title
            tvGenreValue.text = MovieGenres.getMovieGenreById(movie.genreIds[0])
            val popularity = String.format("%.2f", movie.popularity)
            tvPopularity.text = itemView.context.getString(R.string.popularity_format, popularity)
            tvRating.text =  String.format("%.2f", movie.voteAverage)
            Glide.with(itemView.context)
                .load(movie.backdropPath.asRemoteImagePath(ImageSize.ORIGINAL))
                .placeholder(shimmerDrawable)
                .into(ivBackdrop)
            root.setOnClickListener {
                onItemClick?.invoke(movie)
            }
        }
    }
}