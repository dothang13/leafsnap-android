package fxc.dev.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fxc.dev.common.dispatcher.CoroutineDispatchers
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideAppCoroutineScope(dispatchers: CoroutineDispatchers) =
        CoroutineScope(dispatchers.io + SupervisorJob())
}
