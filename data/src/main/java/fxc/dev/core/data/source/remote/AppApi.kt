package fxc.dev.core.data.source.remote

import fxc.dev.core.domain.model.TikDownload
import retrofit2.http.GET
import retrofit2.http.Query

interface AppApi {
    @GET("tiktok-download")
    suspend fun downloadTik(
        @Query("url") url: String
    ): TikDownload
}
