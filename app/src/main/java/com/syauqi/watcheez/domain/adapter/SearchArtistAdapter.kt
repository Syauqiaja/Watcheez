package com.syauqi.watcheez.domain.adapter

import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.syauqi.watcheez.databinding.ItemActorSearchResultBinding
import com.syauqi.watcheez.domain.BaseAdapter
import com.syauqi.watcheez.domain.model.People
import com.syauqi.watcheez.utils.asRemoteImagePath
import com.syauqi.watcheez.utils.enums.ImageSize

class SearchArtistAdapter: BaseAdapter<ItemActorSearchResultBinding, People>(
    inflateMethod = {parent,_ ->
        ItemActorSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }
) {
    override fun bindView(data: People, binding: ItemActorSearchResultBinding, itemView: View) {
        super.bindView(data, binding, itemView)
        binding.apply {
            tvActorName.text = data.name
            tvPopularity.text = String.format("%.2f", data.popularity)
            Glide.with(itemView)
                .load(data.photoUrl.asRemoteImagePath(ImageSize.W_300))
                .placeholder(shimmerDrawable)
                .into(ivActorProfileHead)
        }
    }
}