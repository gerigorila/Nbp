package dev.bondar.nbp_main.currency_rate_info

import dagger.assisted.AssistedFactory

@AssistedFactory
public interface NbpCurrencyRateInfoViewModelFactory {
    public fun create(data: Pair<String, String>): NbpCurrencyRateInfoViewModel
}