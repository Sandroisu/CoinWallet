package ru.slatinin.nytnews.data.nytsections

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.slatinin.nytnews.BuildConfig

interface NytSectionService {

    @GET("svc/topstories/v2/{section}.json")
    suspend fun loadSections(
        @Path("section") section: String,
        @Query("api-key") clientId: String = BuildConfig.NEW_YORK_TIMES
    ): NytSectionResponse

    companion object {
        private const val BASE_URL = "https://api.nytimes.com/"

        fun create(): NytSectionService {
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
                .create(NytSectionService::class.java)
        }
    }
}