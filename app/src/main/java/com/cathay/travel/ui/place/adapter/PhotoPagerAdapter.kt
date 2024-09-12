package com.cathay.travel.ui.place.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.cathay.travel.databinding.PhotoPagerItemBinding

/**
 * 照片 Adapter
 */
class PhotoPagerAdapter : RecyclerView.Adapter<PhotoPagerAdapter.ViewHolder>() {
    private var urlList: List<String> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PhotoPagerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = urlList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val url = urlList[position]
        holder.bind(url)
    }

    fun setPhoto(newList: List<String>) {
        urlList = newList
        notifyDataSetChanged()
    }

    class ViewHolder(
        val binding: PhotoPagerItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(url: String) {
            binding.photoIv.load(url)
        }
    }
}