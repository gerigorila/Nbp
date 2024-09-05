package dev.bondar.nbp_main.currency_rate_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.bondar.nbp_main.toCurrencyState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Provider


@HiltViewModel
public class NbpCurrencyRateInfoViewModel @Inject internal constructor(
    getCurrencyRateInfoUseCase: Provider<GetCurrencyRateInfoUseCase>,
) : ViewModel() {

    public val state: StateFlow<CurrencyState> =
        getCurrencyRateInfoUseCase.get().invoke()
            .map { it.toCurrencyState() }
            .stateIn(viewModelScope, SharingStarted.Lazily, CurrencyState.None)
}