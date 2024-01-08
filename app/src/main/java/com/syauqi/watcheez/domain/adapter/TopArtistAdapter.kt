package com.syauqi.watcheez.domain.adapter

import android.view.LayoutInflater
import android.view.View
import com.bumptech.glide.Glide
import com.syauqi.watcheez.databinding.ItemArtistOfTheYearBinding
import com.syauqi.watcheez.domain.BaseAdapter
import com.syauqi.watcheez.domain.model.People
import com.syauqi.watcheez.utils.asRemoteImagePath
import com.syauqi.watcheez.utils.enums.ImageSize

class TopArtistAdapter: BaseAdapter<ItemArtistOfTheYearBinding, People>(
    inflateMethod = {parent, viewType ->
        ItemArtistOfTheYearBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }
) {
    override fun bindView(data: People, binding: ItemArtistOfTheYearBinding, itemView: View) {
        super.bindView(data, binding, itemView)
        binding.tvActorName.text = data.name
        binding.tvPopularity.text = String.format("%.2f", data.popularity)
        Glide.with(itemView)
            .load(data.photoUrl.asRemoteImagePath(ImageSize.ORIGINAL))
            .placeholder(shimmerDrawable)
            .into(binding.ivActorProfileHead)
    }
}