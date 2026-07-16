package fxc.dev.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fxc.dev.common.bus.BusProvider
import fxc.dev.common.bus.BusProviderImpl
import fxc.dev.common.dispatcher.AppCoroutineDispatchers
import fxc.dev.common.dispatcher.CoroutineDispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {
    @Singleton
    @Provides
    fun provideBusProvider(): BusProvider = BusProviderImpl()

    @Singleton
    @Provides
    fun provideCoroutineDispatchers(): CoroutineDispatchers = AppCoroutineDispatchers()
}