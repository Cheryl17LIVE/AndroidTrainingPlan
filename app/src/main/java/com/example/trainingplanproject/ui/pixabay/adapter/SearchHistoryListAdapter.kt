package com.example.trainingplanproject.ui.pixabay.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.trainingplanproject.databinding.ItemSearchBinding
import com.example.trainingplanproject.db.SearchHistoryData


class SearchHistoryListAdapter(private val onItemClickListener: OnItemClickListener) : ListAdapter<SearchHistoryData, SearchHistoryListAdapter.MyViewHolder>(DiffCallback()) {

    inner class MyViewHolder(val binding: ItemSearchBinding) :  RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryListAdapter.MyViewHolder {
        return MyViewHolder(ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: SearchHistoryListAdapter.MyViewHolder, position: Int) {
        holder.binding.tv.text = getItem(position).query
        holder.binding.tv.setOnClickListener {
            onItemClickListener.onSelect(holder.binding.tv.text.toString())
        }
        holder.binding.btnDelete.setOnClickListener {
            onItemClickListener.onDelete(getItem(position))
        }
    }

}

class DiffCallback : DiffUtil.ItemCallback<SearchHistoryData>() {
    override fun areItemsTheSame(oldItem: SearchHistoryData, newItem: SearchHistoryData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SearchHistoryData, newItem: SearchHistoryData): Boolean {
        return oldItem == newItem
    }
}

class OnItemClickListener(val selectListener: (query: String) -> Unit, val deleteListener: (query: SearchHistoryData) -> Unit) {
    fun onSelect(query: String) = selectListener(query)
    fun onDelete(data: SearchHistoryData) = deleteListener(data)
}
