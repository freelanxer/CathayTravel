package com.cathay.travel.repository

import com.cathay.travel.model.Resource
import com.cathay.travel.model.news.NewsListModel
import com.cathay.travel.model.place.PlaceListModel
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