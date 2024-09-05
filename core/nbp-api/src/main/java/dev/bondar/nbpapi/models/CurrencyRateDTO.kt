package dev.bondar.nbpapi.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyRateDTO(
    @SerialName("no") val no: String?,
    @SerialName("effectiveDate") val effectiveDate: String?,
    @SerialName("mid") val mid: Double?,
)