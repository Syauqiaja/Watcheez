package com.syauqi.watcheez.domain.adapter

import android.view.LayoutInflater
import android.view.View
import com.bumptech.glide.Glide
import com.syauqi.watcheez.core.data.source.network.response.people_detail.ImageItem
import com.syauqi.watcheez.databinding.ItemActorPhotosBinding
import com.syauqi.watcheez.utils.asRemoteImagePath
import com.syauqi.watcheez.utils.enums.ImageSize

class ArtistPhotoAdapter:BaseAdapter<ItemActorPhotosBinding, ImageItem>(
    inflateMethod = {parent, viewType ->
        ItemActorPhotosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }
) {
    override fun bindView(data: ImageItem, binding: ItemActorPhotosBinding, itemView: View) {
        super.bindView(data, binding, itemView)
        Glide.with(itemView).load(data.filePath.asRemoteImagePath(ImageSize.W_300)).into(binding.ivPhoto)
    }
}