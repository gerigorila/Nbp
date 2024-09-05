package dev.bondar.nbpapi.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CurrencyResponseDTO<E>(
    @SerialName("table") val table: String,
    @SerialName("currency") val currency: String,
    @SerialName("code") val code: String,
    @SerialName("rates") val rates: List<E>
)