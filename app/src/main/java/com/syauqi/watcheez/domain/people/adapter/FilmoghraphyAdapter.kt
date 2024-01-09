package com.syauqi.watcheez.domain.people.adapter

import android.view.LayoutInflater
import android.view.View
import com.bumptech.glide.Glide
import com.syauqi.watcheez.core.data.source.network.response.people_detail.CastItem
import com.syauqi.watcheez.databinding.ItemFilmographyBinding
import com.syauqi.watcheez.domain.BaseAdapter
import com.syauqi.watcheez.utils.MovieGenres
import com.syauqi.watcheez.utils.asRemoteImagePath
import com.syauqi.watcheez.utils.enums.ImageSize

class FilmoghraphyAdapter: BaseAdapter<ItemFilmographyBinding, CastItem>(
    inflateMethod = {parent, _ ->
        ItemFilmographyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }
){
    override fun bindView(data: CastItem, binding: ItemFilmographyBinding, itemView: View) {
        super.bindView(data, binding, itemView)
        binding.apply {
            Glide.with(itemView)
                .load(data.backdropPath?.asRemoteImagePath(ImageSize.ORIGINAL))
                .placeholder(shimmerDrawable)
                .into(ivBackdrop)
            tvTitle.text = data.title
            data.genreIds?.get(0)?.let {tvGenre.text = MovieGenres.getMovieGenreById(it)}
            tvRating.text = String.format("%.2f", data.popularity)
        }
    }
}