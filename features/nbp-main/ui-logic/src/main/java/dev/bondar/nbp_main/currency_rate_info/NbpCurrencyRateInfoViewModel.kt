package dev.bondar.nbp_main.currency_rate_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.bondar.nbp_main.currency_rate_info.usecase.GetCurrencyRateInfoUseCase
import dev.bondar.nbp_main.toCurrencyState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Provider


@HiltViewModel(assistedFactory = NbpCurrencyRateInfoViewModelFactory::class)
public class NbpCurrencyRateInfoViewModel @AssistedInject internal constructor(
    @Assisted public val data: Pair<String, String>,
    getCurrencyRateInfoUseCase: Provider<GetCurrencyRateInfoUseCase>,
) : ViewModel() {

    public val state: StateFlow<CurrencyState> =
        getCurrencyRateInfoUseCase.get().invoke(code = data.first, table = data.second)
            .map { it.toCurrencyState() }
            .stateIn(viewModelScope, SharingStarted.Lazily, CurrencyState.None)
}