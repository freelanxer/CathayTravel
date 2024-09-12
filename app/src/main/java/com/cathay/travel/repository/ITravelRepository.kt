package com.cathay.travel.repository

import com.cathay.travel.model.Resource
import com.cathay.travel.model.news.NewsListModel
import com.cathay.travel.model.place.PlaceListModel
import kotlinx.coroutines.flow.Flow

/**
 * 旅遊台北 Repository Interface
 * - 最新消息
 * - 景點
 */
interface ITravelRepository {
    /**
     * 取得最新消息資料
     */
    suspend fun getNews(
        lang: String,
        begin: String? = null,
        end: String? = null,
        page: Int = 1,
    ): Flow<Resource<NewsListModel>>

    /**
     * 取得景點資料
     */
    suspend fun getPlaceList(
        lang: String,
        categoryIds: String? = null,
        nLat: Double? = null,
        eLong: Double? = null,
        page: Int = 1,
    ): Flow<Resource<PlaceListModel>>
}