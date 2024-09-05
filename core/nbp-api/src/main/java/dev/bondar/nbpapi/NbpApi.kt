package dev.bondar.nbpapi

import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import dev.bondar.nbpapi.models.CurrencyRateDTO
import dev.bondar.nbpapi.models.CurrencyResponseDTO
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

    @GET("rates/{table}/{code}/{startDate}/{endDate}/")
    suspend fun currencyRate(
        @Path("table") table: String? = null,
        @Path("code") code: String? = null,
        @Path("startDate") startDate: String? = null,
        @Path("endDate") endDate: String? = null,
    ): Result<CurrencyResponseDTO<CurrencyRateDTO>>
}

fun NbpApi(
    baseUrl: String,
    json: Json = Json,
    okHttpClient: OkHttpClient,
): NbpApi {
    return retrofit(baseUrl, okHttpClient, json).create()
}

private fun retrofit(
    baseUrl: String,
    okHttpClient: OkHttpClient,
    json: Json,
): Retrofit {
    val jsonConverterFactory = json.asConverterFactory("application/json".toMediaType())

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(jsonConverterFactory)
        .addCallAdapterFactory(ResultCallAdapterFactory.create())
        .client(okHttpClient)
        .build()
}