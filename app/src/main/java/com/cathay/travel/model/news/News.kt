package com.cathay.travel.model.news


import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("begin")
    val begin: Any,
    @SerializedName("description")
    val description: String,
    @SerializedName("end")
    val end: Any,
    @SerializedName("files")
    val files: List<File>?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("links")
    val links: List<Link>?,
    @SerializedName("modified")
    val modified: String,
    @SerializedName("posted")
    val posted: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String
)

class NewsDiffUtil(
    private val oldList: List<News>,
    private val newList: List<News>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}