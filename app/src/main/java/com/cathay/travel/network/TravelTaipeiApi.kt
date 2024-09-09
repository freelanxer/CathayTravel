package com.cathay.travel.network

import com.cathay.travel.data.news.NewsListModel
import com.cathay.travel.data.place.PlaceListModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TravelTaipeiApi {

    @GET("{lang}/Events/News")
    suspend fun news(
        @Path("lang")
        lang: String = "zh-tw",
        @Query("begin")
        begin: String? = null,
        @Query("end")
        end: String? = null,
        @Query("page")
        page: Int = 1,
    ): NewsListModel

    @GET("{lang}/Attractions/All")
    suspend fun placeList(
        @Path("lang")
        lang: String = "zh-tw",
        @Query("categoryIds")
        categoryIds: String? = null,
        @Query("nlat")
        nLat: Double? = null,
        @Query("elong")
        eLong: Double? = null,
        @Query("page")
        page: Int = 1,
    ): PlaceListModel
}