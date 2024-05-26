package com.syauqi.watcheez.presentation.features.movie_detail.adapter

import android.view.LayoutInflater
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.syauqi.watcheez.databinding.ItemWatchProviderBinding
import com.syauqi.watcheez.domain.BaseAdapter
import com.syauqi.watcheez.domain.movie.model.MovieProviders
import com.syauqi.watcheez.utils.asRemoteImagePath
import com.syauqi.watcheez.utils.enums.ImageSize

class WatchProviderAdapter:BaseAdapter<ItemWatchProviderBinding, MovieProviders.WatchProvider>(
    inflateMethod = {parent, _ ->
        ItemWatchProviderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }
) {
    override fun bindView(data: MovieProviders.WatchProvider, binding: ItemWatchProviderBinding, itemView: View) {
        binding.apply {
            tvProvider.text = data.providerName
            Glide.with(itemView.context)
                .load(data.logoPath.asRemoteImagePath(ImageSize.ORIGINAL))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivProvider)
        }
    }
}