package com.cathay.travel.data.place


import com.google.gson.annotations.SerializedName

data class PlaceListModel(
    @SerializedName("data")
    val placeList: List<Place>,
    @SerializedName("total")
    val total: Int
)