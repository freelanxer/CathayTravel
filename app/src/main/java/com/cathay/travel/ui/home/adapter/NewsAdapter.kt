package com.cathay.travel.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cathay.travel.data.news.News
import com.cathay.travel.data.news.NewsDiffUtil
import com.cathay.travel.databinding.ListItemNewsBinding
import com.cathay.travel.ui.home.HomeViewModel

class NewsAdapter(
    private val viewModel: HomeViewModel
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    private var list: List<News> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun setData(newList: List<News>) {
//        val callback = NewsDiffUtil(list, newList)
//        val result = DiffUtil.calculateDiff(callback)
//        list = newList
//        result.dispatchUpdatesTo(this)
        list = newList.subList(0,3)
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val binding: ListItemNewsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News) {
            binding.titleTv.text = news.title
            binding.descriptionTv.text = news.description
        }
    }

}