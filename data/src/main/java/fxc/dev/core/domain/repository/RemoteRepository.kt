package fxc.dev.core.domain.repository

import fxc.dev.core.domain.model.TikDownload
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {
    fun downloadTik(url: String): Flow<TikDownload>
}