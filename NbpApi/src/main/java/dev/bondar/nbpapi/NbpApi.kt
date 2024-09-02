package dev.bondar.nbpapi

import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import dev.bondar.nbpapi.models.RateDTO
import dev.bondar.nbpapi.models.ResponseDTO
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path

interface NbpApi {

    /** API details [here](https://api.nbp.pl/) */

    @GET("tables/{table}/")
    suspend fun table(
        @Path("table") query: String? = null,
    ): Result<List<ResponseDTO<RateDTO>>>

    fun NbpApi(
        baseUrl: String,
        okHttpClient: OkHttpClient? = null,
        json: Json = Json,
    ): NbpApi {
        return retrofit(baseUrl, json).create()
    }

    private fun retrofit(
        baseUrl: String,
        json: Json,
    ): Retrofit {
        val jsonConverterFactory = json.asConverterFactory("application/json".toMediaType())

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(jsonConverterFactory)
            .addCallAdapterFactory(ResultCallAdapterFactory.create())
            .build()
    }
}