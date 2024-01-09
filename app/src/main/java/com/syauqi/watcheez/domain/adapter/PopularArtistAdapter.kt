package com.syauqi.watcheez.domain.adapter

import android.view.LayoutInflater
import android.view.View
import com.bumptech.glide.Glide
import com.syauqi.watcheez.domain.model.People
import com.syauqi.watcheez.databinding.ItemActorPosterBinding
import com.syauqi.watcheez.domain.BaseAdapter
import com.syauqi.watcheez.utils.enums.ImageSize
import com.syauqi.watcheez.utils.asRemoteImagePath

class PopularArtistAdapter : BaseAdapter<ItemActorPosterBinding, People>(
    inflateMethod = {parent, viewType ->
        ItemActorPosterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    }
) {
    override fun bindView(data: People, binding: ItemActorPosterBinding, itemView: View) {
        super.bindView(data, binding, itemView)
        binding.tvActorName.text = data.name
        binding.tvPopularity.text = String.format("%.2f", data.popularity)
        Glide.with(itemView)
            .load(data.photoUrl.asRemoteImagePath(ImageSize.ORIGINAL))
            .placeholder(shimmerDrawable)
            .into(binding.ivActorProfileHead)
    }
}