package com.cathay.travel.di

import com.cathay.travel.Constants
import com.cathay.travel.network.TravelTaipeiApi
import com.cathay.travel.repository.ITravelRepository
import com.cathay.travel.repository.TravelRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                val newRequest = request
                    .newBuilder()
                    .addHeader("Accept", "application/json")
                    .build()
                chain.proceed(newRequest)
            }
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BASIC
                }
            )
            .build()

    @Provides
    @Singleton
    fun provideTravelTaipeiApi(httpClient: OkHttpClient): TravelTaipeiApi =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TravelTaipeiApi::class.java)

    @Provides
    @Singleton
    fun provideTravelRepository(travelApi: TravelTaipeiApi): ITravelRepository =
        TravelRepository(travelApi)
}