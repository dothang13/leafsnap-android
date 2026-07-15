package fxc.dev.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import f.x.c.secrets.BuildConfig
import f.x.c.secrets.Secrets
import fxc.dev.core.utils.AESDecrypt
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideAesDecrypt(): AESDecrypt {
        val secrets = Secrets()
        return AESDecrypt(secrets.getiv(BuildConfig.LIBRARY_PACKAGE_NAME), secrets.getsecret(BuildConfig.LIBRARY_PACKAGE_NAME))
    }
}