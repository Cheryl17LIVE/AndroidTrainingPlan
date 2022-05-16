package com.example.trainingplanproject.ui.pixabay

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.trainingplanproject.databinding.ItemImageBinding
import com.example.trainingplanproject.network.model.pixabay.PixabayItem
import com.example.trainingplanproject.util.glideLoadImg
import com.example.trainingplanproject.util.setImgLink

class PixabayPagingAdapter : PagingDataAdapter<PixabayItem, PixabayPagingAdapter.MyViewHolder>(diffCallback) {

    inner class MyViewHolder(val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<PixabayItem>() {
            override fun areItemsTheSame(oldItem: PixabayItem, newItem: PixabayItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PixabayItem, newItem: PixabayItem): Boolean {
                return oldItem == newItem
            }
        }
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currChar = getItem(position)

        holder.binding.imageView.glideLoadImg(currChar?.previewURL)
        holder.binding.imageView.setImgLink(currChar?.webformatURL)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

}