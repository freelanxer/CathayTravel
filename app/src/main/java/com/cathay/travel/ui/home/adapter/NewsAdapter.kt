package com.cathay.travel.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cathay.travel.model.news.News
import com.cathay.travel.databinding.ListItemNewsBinding

/**
 * 最新消息列表 Adapter
 */
class NewsAdapter(
    private val listener: Listener
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
        val news = list[position]
        holder.bind(news)
        holder.itemView.setOnClickListener {
            listener.onNewsClicked(news)
        }
    }

    fun setData(newList: List<News>) {
        list = if (newList.size >= 3) newList.subList(0, 3) else newList
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

    interface Listener {
        fun onNewsClicked(news: News)
    }
}