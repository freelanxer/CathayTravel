package com.cathay.travel.data.news


import com.google.gson.annotations.SerializedName

data class File(
    @SerializedName("ext")
    val ext: String,
    @SerializedName("src")
    val src: String,
    @SerializedName("subject")
    val subject: String
)