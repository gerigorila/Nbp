package dev.bondar.nbp.data

import dev.bondar.nbp.data.model.CurrencyRate
import dev.bondar.nbp.data.model.Rate
import dev.bondar.nbpapi.models.CurrencyRateDTO
import dev.bondar.nbpapi.models.RateDTO

internal fun RateDTO.toRates(): Rate {
    return Rate(
        currency = currency,
        code = code,
        mid = mid,
    )
}

internal fun CurrencyRateDTO.toCurrencyRate(currency: String, code: String): CurrencyRate {
    return CurrencyRate(
        currency = currency,
        code = code,
        no = no,
        effectiveDate = effectiveDate,
        mid = mid,
    )
}