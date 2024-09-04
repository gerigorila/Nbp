package dev.bondar.nbp
import okhttp3.logging.HttpLoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.bondar.common.AndroidLogcatLogger
import dev.bondar.common.Logger
import dev.bondar.nbpapi.NbpApi
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNbpApi(okHttpClient: OkHttpClient): NbpApi {
        return NbpApi(
            baseUrl = BuildConfig.NBP_API_BASE_URL,
            okHttpClient = okHttpClient
        )
    }

    @Provides
    fun provideLogger(): Logger = AndroidLogcatLogger()

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val logging =
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }
}