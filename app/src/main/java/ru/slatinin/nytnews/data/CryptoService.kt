package ru.slatinin.nytnews.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import ru.slatinin.nytnews.BuildConfig
import ru.slatinin.nytnews.data.crypto.CryptoCurrencyResponse

interface CryptoService {
    @Headers(
        "Accept: application/json",
        "X-CMC_PRO_API_KEY: ${BuildConfig.COIN_MARKET_CAP}"
    )
    @GET("v1/cryptocurrency/quotes/latest")
    suspend fun searchCrypto(
        @Query("id") id: Int
    ): CryptoCurrencyResponse

    companion object {
        private const val BASE_URL = "https://pro-api.coinmarketcap.com/"

        fun create(): CryptoService {
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
                .create(CryptoService::class.java)
        }
    }
}