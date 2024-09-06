package dev.bondar.nbp_main

import dev.bondar.nbp.data.RequestResult
import dev.bondar.nbp.data.model.CurrencyRate
import dev.bondar.nbp.data.model.Rate
import dev.bondar.nbp_main.currency_rate_info.CurrencyRateUI
import dev.bondar.nbp_main.currency_rate_info.CurrencyState
import dev.bondar.nbp_main.main.RateUI
import dev.bondar.nbp_main.main.State
import kotlinx.collections.immutable.toImmutableList

internal fun Rate.toUiRates(table: String?): RateUI {
    return RateUI(
        currency = currency,
        code = code,
        mid = mid,
        table = table
    )
}

internal fun CurrencyRate.toUiCurrencyRates(): CurrencyRateUI {
    return CurrencyRateUI(
        no = no,
        effectiveDate = effectiveDate,
        mid = mid,
        currency = currency,
        code = code,
        color = color
    )
}

internal fun RequestResult<List<RateUI>>.toState(): State {
    return when (this) {
        is RequestResult.Error -> State.Error(data?.toImmutableList())
        is RequestResult.InProgress -> State.Loading(data?.toImmutableList())
        is RequestResult.Success -> State.Success(data.toImmutableList())
    }
}

internal fun RequestResult<List<CurrencyRateUI>>.toCurrencyState(): CurrencyState {
    return when (this) {
        is RequestResult.Error -> CurrencyState.Error(data?.toImmutableList())
        is RequestResult.InProgress -> CurrencyState.Loading(data?.toImmutableList())
        is RequestResult.Success -> CurrencyState.Success(data.toImmutableList())
    }
}