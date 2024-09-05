package dev.bondar.nbp_main.currency_rate_info

import dev.bondar.nbp.data.RatesRepository
import dev.bondar.nbp.data.RequestResult
import dev.bondar.nbp.data.map
import dev.bondar.nbp_main.toUiCurrencyRates
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


internal class GetCurrencyRateInfoUseCase @Inject constructor(
    private val repository: RatesRepository,
) {
    operator fun invoke(): Flow<RequestResult<List<CurrencyRateUI>>> {
        return repository.getCurrencyRateInfoFromServer().map { requestResult ->
            requestResult.map { rates -> rates.map { it.toUiCurrencyRates() } }
        }
    }
}