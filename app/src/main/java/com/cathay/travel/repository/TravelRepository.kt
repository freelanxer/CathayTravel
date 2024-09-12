package com.cathay.travel.repository

import com.cathay.travel.model.Resource
import com.cathay.travel.model.news.NewsListModel
import com.cathay.travel.model.place.PlaceListModel
import com.cathay.travel.network.TravelTaipeiApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * 旅遊台北 Repository
 * - 最新消息
 * - 景點
 */
class TravelRepository @Inject constructor(
    private val travelApi: TravelTaipeiApi,
) : ITravelRepository {
    /**
     * 取得最新消息資料
     */
    override suspend fun getNews(
        lang: String,
        begin: String?,
        end: String?,
        page: Int
    ): Flow<Resource<NewsListModel>> {
        return flow {
            try {
                emit(Resource.Loading())
                val result = travelApi.news(lang, begin, end, page)
                emit(Resource.Success(data = result))
            } catch (ex: Exception) {
                emit(Resource.Error(message = ex.message))
            }
        }
    }

    /**
     * 取得景點資料
     */
    override suspend fun getPlaceList(
        lang: String,
        categoryIds: String?,
        nLat: Double?,
        eLong: Double?,
        page: Int
    ): Flow<Resource<PlaceListModel>> {
        return flow {
            try {
                emit(Resource.Loading())
                val result = travelApi.placeList(lang, categoryIds, nLat, eLong, page)
                emit(Resource.Success(data = result))
            } catch (ex: Exception) {
                emit(Resource.Error(message = ex.message))
            }
        }
    }

}