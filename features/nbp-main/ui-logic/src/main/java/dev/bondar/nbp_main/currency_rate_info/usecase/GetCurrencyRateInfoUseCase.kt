package dev.bondar.nbp_main.currency_rate_info.usecase

import dev.bondar.nbp.data.RatesRepository
import dev.bondar.nbp.data.RequestResult
import dev.bondar.nbp.data.map
import dev.bondar.nbp_main.currency_rate_info.CurrencyRateUI
import dev.bondar.nbp_main.toUiCurrencyRates
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


internal class GetCurrencyRateInfoUseCase @Inject constructor(
    private val repository: RatesRepository,
) {
    operator fun invoke(code: String, table: String): Flow<RequestResult<List<CurrencyRateUI>>> {
        return repository.getCurrencyRateInfoFromServer(code,table).map { requestResult ->
            requestResult.map { rates -> rates.map { it.toUiCurrencyRates() } }
        }
    }
}