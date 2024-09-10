package com.cathay.travel.data.news


import com.google.gson.annotations.SerializedName

data class NewsListModel(
    @SerializedName("data")
    val newsList: List<News>,
    @SerializedName("total")
    val total: Int
)