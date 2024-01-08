package com.syauqi.watcheez.domain.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.syauqi.watcheez.domain.models.People
import com.syauqi.watcheez.databinding.ItemActorPosterBinding
import com.syauqi.watcheez.utils.enums.ImageSize
import com.syauqi.watcheez.utils.asRemoteImagePath

class TrendingArtistAdapter : RecyclerView.Adapter<TrendingArtistAdapter.ListViewHolder>() {

    lateinit var onItemClick: (People) -> Unit
    private var listData = ArrayList<People>()

    fun setData(newListData: List<People>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyItemRangeInserted(0, listData.size)
    }

    inner class ListViewHolder(private val binding: ItemActorPosterBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: People){
            binding.tvActorName.text = data.name
            binding.tvPopularity.text = String.format("%.2f", data.popularity)
            Glide.with(itemView).load(data.photoUrl.asRemoteImagePath(ImageSize.ORIGINAL)).into(binding.ivActorProfileHead)
        }
        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(ItemActorPosterBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listData[position])
    }
}