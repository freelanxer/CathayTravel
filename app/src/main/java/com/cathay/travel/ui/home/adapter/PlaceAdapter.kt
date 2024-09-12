package com.cathay.travel.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.cathay.travel.R
import com.cathay.travel.model.place.Place
import com.cathay.travel.databinding.ListItemPlaceBinding

/**
 * 景點列表 Adapter
 */
class PlaceAdapter(
    private val listener: Listener
) : RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {
    private var list: List<Place> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = list[position]
        holder.bind(place)
        holder.itemView.setOnClickListener {
            listener.onPlaceClicked(place)
        }
    }

    fun setData(newList: List<Place>) {
        list = newList
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val binding: ListItemPlaceBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(place: Place) {
            binding.titleTv.text = place.name
            binding.descriptionTv.text = place.introduction
            if (place.images.isNullOrEmpty()) {
                binding.photoIv.load(R.drawable.placeholder)
            } else {
                binding.photoIv.load(
                    place.images.first().src,
                    builder = { placeholder(R.drawable.placeholder) }
                )
            }
        }
    }
    interface Listener {
        fun onPlaceClicked(place: Place)
    }
}