package fxc.dev.core.di

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fxc.dev.core.NetworkConstant
import fxc.dev.core.data.source.remote.AppApi
import fxc.dev.core.utils.interceptor.DecryptInterceptor
import fxc.dev.core.utils.interceptor.NetworkInterceptor
import fxc.dev.data.BuildConfig
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun providesConverterFactory(): Converter.Factory {
        val json = Json { ignoreUnknownKeys = true }
        val contentType = "application/json".toMediaType()
        return json.asConverterFactory(contentType)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        networkInterceptor: NetworkInterceptor,
        decryptInterceptor: DecryptInterceptor
    ): OkHttpClient {
        val myCache = Cache(context.cacheDir, NetworkConstant.CACHE_SIZE)

        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

        val builder = OkHttpClient.Builder()
            .cache(myCache)
            .addInterceptor(networkInterceptor)
            .apply {
                if (!NetworkConstant.IS_DEBUG) {
                    addInterceptor(decryptInterceptor)
                }
            }
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(NetworkConstant.TIME_OUT, TimeUnit.MILLISECONDS)
            .writeTimeout(NetworkConstant.TIME_OUT, TimeUnit.MILLISECONDS)
            .readTimeout(NetworkConstant.TIME_OUT, TimeUnit.MILLISECONDS)
        return builder.build()
    }

    @Singleton
    @Provides
    fun appApi(
        client: OkHttpClient,
        converterFactory: Converter.Factory
    ): AppApi {
        return Retrofit.Builder()
            .baseUrl(NetworkConstant.API_SERVER)
            .client(client)
            .addConverterFactory(converterFactory)
            .build()
            .create(AppApi::class.java)
    }
}