package dev.bondar.nbp

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.bondar.nbpapi.NbpApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNbpApi(): NbpApi {
        return NbpApi(baseUrl = BuildConfig.NBP_API_BASE_URL)
    }
}