package com.cathay.travel.data.news


import com.google.gson.annotations.SerializedName

data class NewsListModel(
    @SerializedName("data")
    val newsList: List<Data>,
    @SerializedName("total")
    val total: Int
)