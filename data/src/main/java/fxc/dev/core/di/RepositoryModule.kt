package fxc.dev.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fxc.dev.core.data.repository.LocalRepositoryImpl
import fxc.dev.core.data.repository.RemoteRepositoryImpl
import fxc.dev.core.domain.repository.LocalRepository
import fxc.dev.core.domain.repository.RemoteRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindRemoteRepository(impl: RemoteRepositoryImpl): RemoteRepository

    @Binds
    @Singleton
    fun bindLocalRepository(impl: LocalRepositoryImpl): LocalRepository
}
