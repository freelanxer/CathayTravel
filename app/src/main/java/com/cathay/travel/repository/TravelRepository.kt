package com.cathay.travel.repository

import com.cathay.travel.data.Resource
import com.cathay.travel.data.news.NewsListModel
import com.cathay.travel.data.place.PlaceListModel
import com.cathay.travel.network.TravelTaipeiApi
import javax.inject.Inject

class TravelRepository @Inject constructor(
    private val travelApi: TravelTaipeiApi,
) : ITravelRepository {
    override suspend fun getNews(
        lang: String,
        begin: String?,
        end: String?,
        page: Int
    ): Resource<NewsListModel> {
        return try {
            Resource.Loading(data = true)
            val result = travelApi.news(lang, begin, end, page)
            Resource.Success(data = result)
        } catch (ex: Exception) {
            Resource.Error(message = "")
        }
    }

    override suspend fun getPlaceList(
        lang: String,
        categoryIds: String?,
        nLat: Double?,
        eLong: Double?,
        page: Int
    ): Resource<PlaceListModel> {
        return try {
            Resource.Loading(data = true)
            val result = travelApi.placeList(lang, categoryIds, nLat, eLong, page)
            Resource.Success(data = result)
        } catch (ex: Exception) {
            Resource.Error(message = "")
        }
    }

}