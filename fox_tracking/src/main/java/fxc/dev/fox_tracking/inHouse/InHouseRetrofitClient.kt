package fxc.dev.fox_tracking.inHouse

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import fxc.dev.fox_tracking.BuildConfig
import java.util.concurrent.TimeUnit
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

/**
 * InHouseRetrofitClient is an object that provides a Retrofit client for the in-house tracking API.
 */
@OptIn(ExperimentalSerializationApi::class)
object InHouseRetrofitClient {
    private const val TIME_OUT = 15_000L

    /**
     * The API service for making tracking requests.
     */
    fun create(trackingUrl: String): InHouseTrackingApi {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        }

        val okHttpBuilder = OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)

        val json = Json { ignoreUnknownKeys = true }
        val contentType = "application/json".toMediaType()
        val jsonConverterFactory = json.asConverterFactory(contentType)

        return Retrofit
            .Builder()
            .baseUrl(trackingUrl)
            .client(okHttpBuilder.build())
            .addConverterFactory(jsonConverterFactory)
            .build()
            .create(InHouseTrackingApi::class.java)
    }
}
