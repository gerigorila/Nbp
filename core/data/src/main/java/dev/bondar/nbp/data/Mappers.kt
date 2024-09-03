package dev.bondar.nbp.data

import dev.bondar.nbp.data.model.Rate
import dev.bondar.nbpapi.models.RateDTO

internal fun RateDTO.toRates(): Rate {
    return Rate(
        currency = currency,
        code = code,
        mid = mid,
    )
}