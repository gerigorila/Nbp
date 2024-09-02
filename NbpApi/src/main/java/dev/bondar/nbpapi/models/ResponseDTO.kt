package dev.bondar.nbpapi.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseDTO<E>(
    @SerialName("table") val table: String,
    @SerialName("no") val no: String,
    @SerialName("effectiveDate") val effectiveDate: String,
    @SerialName("rates") val rates: List<E>
)