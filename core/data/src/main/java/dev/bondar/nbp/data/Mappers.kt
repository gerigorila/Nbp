package dev.bondar.nbp.data

import dev.bondar.nbp.data.model.CurrencyRate
import dev.bondar.nbp.data.model.Rate
import dev.bondar.nbpapi.models.CurrencyRateDTO
import dev.bondar.nbpapi.models.RateDTO

internal fun RateDTO.toRates(table: String): Rate {
    return Rate(
        currency = currency,
        code = code,
        mid = mid,
        table = table,
    )
}

internal fun CurrencyRateDTO.toCurrencyRate(currency: String, code: String, color: Int): CurrencyRate {
    return CurrencyRate(
        currency = currency,
        code = code,
        no = no,
        effectiveDate = effectiveDate,
        mid = mid,
        color = color,
    )
}