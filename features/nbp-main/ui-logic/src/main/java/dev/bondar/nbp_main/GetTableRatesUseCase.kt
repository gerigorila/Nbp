package dev.bondar.nbp_main

import dev.bondar.nbp.data.RatesRepository
import dev.bondar.nbp.data.RequestResult
import dev.bondar.nbp.data.map
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class GetTableRatesUseCase @Inject constructor(
    private val repository: RatesRepository,
) {
    operator fun invoke(query: String): Flow<RequestResult<List<RateUI>>> {
        return repository.getTableRatesFromServer(query).map { requestResult ->
            requestResult.map { rates -> rates.map { it.toUiRates() } }
        }
    }
}