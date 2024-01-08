package com.syauqi.watcheez.domain.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

open class BaseAdapter<B: ViewBinding, T>(
    private val inflateMethod : (ViewGroup, Int) -> B,

):RecyclerView.Adapter<BaseAdapter<B,T>.ListViewHolder>() {
    private val _listData = ArrayList<T>()
    fun setData(datas: List<T>){
        _listData.clear()
        _listData.addAll(datas)
        notifyItemRangeInserted(0,datas.size)
    }
    open fun bindView(data: T, binding: B, itemView: View){}
    inner class ListViewHolder(private val binding: B): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: T){
            bindView(data, binding, itemView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(inflateMethod.invoke(parent, viewType))
    }

    override fun getItemCount(): Int = _listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(_listData[position])
    }
}