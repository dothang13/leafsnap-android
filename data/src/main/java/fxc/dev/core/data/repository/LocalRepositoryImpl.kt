package fxc.dev.core.data.repository

import fxc.dev.common.dispatcher.CoroutineDispatchers
import fxc.dev.core.data.source.local.AppDatabase
import fxc.dev.core.domain.repository.LocalRepository
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutineDispatchers,
    private val appDatabase: AppDatabase
) : LocalRepository {

}
