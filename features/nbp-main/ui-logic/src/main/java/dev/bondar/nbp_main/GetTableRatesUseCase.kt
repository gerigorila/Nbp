package dev.bondar.nbp_main

import dev.bondar.nbp.data.RatesRepository
import dev.bondar.nbp.data.RequestResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

internal class GetTableRatesUseCase(
    private val repository: RatesRepository,
) {
    operator fun invoke(query: String): Flow<RequestResult<List<RateUI>>> {
        return flowOf()
    }
}