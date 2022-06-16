package ru.slatinin.nytnews.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import ru.slatinin.nytnews.BuildConfig
import ru.slatinin.nytnews.data.news.NewsSearchResponse

interface NewsService {

    @GET("svc/search/v2/articlesearch.json")
    suspend fun searchPhotos(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("api-key") clientId: String = BuildConfig.NEW_YORK_TIMES
    ): NewsSearchResponse

    companion object {
        private const val BASE_URL = "https://api.nytimes.com/"

        fun create(): NewsService {
            val logger =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NewsService::class.java)
        }
    }
}