package com.cathay.travel.repository

import com.cathay.travel.data.Resource
import com.cathay.travel.data.news.NewsListModel
import com.cathay.travel.data.place.PlaceListModel
import kotlinx.coroutines.flow.Flow

interface ITravelRepository {
    suspend fun getNews(
        lang: String,
        begin: String? = null,
        end: String? = null,
        page: Int = 1,
    ): Flow<Resource<NewsListModel>>

    suspend fun getPlaceList(
        lang: String,
        categoryIds: String? = null,
        nLat: Double? = null,
        eLong: Double? = null,
        page: Int = 1,
    ): Flow<Resource<PlaceListModel>>
}