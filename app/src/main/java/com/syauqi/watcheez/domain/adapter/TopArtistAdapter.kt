package com.syauqi.watcheez.domain.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.syauqi.watcheez.databinding.ItemArtistOfTheYearBinding
import com.syauqi.watcheez.domain.models.People
import com.syauqi.watcheez.utils.asRemoteImagePath
import com.syauqi.watcheez.utils.enums.ImageSize

class TopArtistAdapter: RecyclerView.Adapter<TopArtistAdapter.ListViewHolder>() {

    private val _listData = ArrayList<People>()
    var onItemClick : ((People) -> Unit)? = null
    fun setData(data: List<People>){
        _listData.clear()
        _listData.addAll(data)
        notifyItemRangeInserted(0, data.size)
    }


    inner class ListViewHolder(private val binding: ItemArtistOfTheYearBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: People){
            binding.tvActorName.text = data.name
            binding.tvPopularity.text = String.format("%.2f", data.popularity)
            Glide.with(itemView)
                .load(data.photoUrl.asRemoteImagePath(ImageSize.ORIGINAL))
                .into(binding.ivActorProfileHead)
        }
        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(_listData[adapterPosition])
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(ItemArtistOfTheYearBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return _listData.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(_listData[position])
    }
}