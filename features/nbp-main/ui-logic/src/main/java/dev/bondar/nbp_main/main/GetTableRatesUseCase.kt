package dev.bondar.nbp_main.main

import dev.bondar.nbp.data.RatesRepository
import dev.bondar.nbp.data.RequestResult
import dev.bondar.nbp.data.map
import dev.bondar.nbp_main.toUiRates
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class GetTableRatesUseCase @Inject constructor(
    private val repository: RatesRepository,
) {
    operator fun invoke(): Flow<RequestResult<List<RateUI>>> {
        return repository.getTableRatesFromServer().map { requestResult ->
            requestResult.map { rates -> rates.map { it.toUiRates(rates.first().table) } }
        }
    }
}