package dev.bondar.nbp_main.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.bondar.nbp_main.main.usecase.GetTableRatesUseCase
import dev.bondar.nbp_main.toState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
public class NbpMainViewModel @Inject internal constructor(
    getTableUseCase: Provider<GetTableRatesUseCase>
) : ViewModel() {

    public val state: StateFlow<State> =
        getTableUseCase.get().invoke()
            .map { it.toState() }
            .stateIn(viewModelScope, SharingStarted.Lazily, State.None)
}