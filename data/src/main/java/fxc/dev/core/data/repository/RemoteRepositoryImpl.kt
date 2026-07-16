package fxc.dev.core.data.repository

import fxc.dev.common.dispatcher.CoroutineDispatchers
import fxc.dev.core.data.source.remote.AppApi
import fxc.dev.core.domain.model.TikDownload
import fxc.dev.core.domain.repository.RemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutineDispatchers,
    private val appApi: AppApi
) : RemoteRepository {
    override fun downloadTik(url: String): Flow<TikDownload> = flow {
        emit(appApi.downloadTik(url))
    }.flowOn(dispatcher.io)
}
