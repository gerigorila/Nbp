package dev.bondar.nbp_main.currency_rate_info

import kotlinx.collections.immutable.ImmutableList

public sealed class CurrencyState(public open val rates: ImmutableList<CurrencyRateUI>?) {

    public data object None : CurrencyState(rates = null )

    public class Loading(rates: ImmutableList<CurrencyRateUI>? = null) : CurrencyState(rates)

    public class Error(rates: ImmutableList<CurrencyRateUI>? = null) : CurrencyState(rates)

    public class Success(override val rates: ImmutableList<CurrencyRateUI>) : CurrencyState(rates)
}