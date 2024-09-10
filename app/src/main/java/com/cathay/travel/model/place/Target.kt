package com.cathay.travel.model.place


import com.google.gson.annotations.SerializedName

data class Target(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)