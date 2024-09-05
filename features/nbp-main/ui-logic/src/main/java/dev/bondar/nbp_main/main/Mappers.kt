package dev.bondar.nbp_main.main

import dev.bondar.nbp.data.RequestResult
import dev.bondar.nbp.data.model.Rate
import kotlinx.collections.immutable.toImmutableList

internal fun Rate.toUiRates(): RateUI {
    return RateUI(
        currency = currency,
        code = code,
        mid = mid,
    )
}

internal fun RequestResult<List<RateUI>>.toState(): State {
    return when (this) {
        is RequestResult.Error -> State.Error(data?.toImmutableList())
        is RequestResult.InProgress -> State.Loading(data?.toImmutableList())
        is RequestResult.Success -> State.Success(data.toImmutableList())
    }
}