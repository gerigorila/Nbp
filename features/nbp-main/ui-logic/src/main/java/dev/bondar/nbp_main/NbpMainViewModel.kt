package dev.bondar.nbp_main

import androidx.lifecycle.ViewModel
import javax.inject.Provider

public class NbpMainViewModel private constructor(
    getTableUseCase: Provider<GetTableRatesUseCase>
) : ViewModel() {

}