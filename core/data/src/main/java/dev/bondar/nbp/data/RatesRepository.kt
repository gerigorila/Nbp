package dev.bondar.nbp.data

import dev.bondar.common.Logger
import dev.bondar.nbp.data.model.CurrencyRate
import dev.bondar.nbp.data.model.Rate
import dev.bondar.nbpapi.NbpApi
import dev.bondar.nbpapi.models.CurrencyRateDTO
import dev.bondar.nbpapi.models.CurrencyResponseDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

public class RatesRepository @Inject constructor(
    private val api: NbpApi,
    private val logger: Logger,
) {
    public fun getTableRatesFromServer(query: String): Flow<RequestResult<List<Rate>>> {
        val apiRequestTableA = flow { emit(api.table("A")) }
            .onEach { result ->
                if (result.isSuccess) logger.d("AAA", "Success result")
            }
            .onEach { result ->
                if (result.isFailure) {
                    logger.e(
                        "AAA",
                        "Error getting data from server. Cause = ${result.exceptionOrNull()}"
                    )
                }
            }
            .map { it.toRequestResult() }

        val apiRequestTableB = flow { emit(api.table("B")) }
            .onEach { result ->
                if (result.isSuccess) logger.d("AAA", "Success result")
            }
            .onEach { result ->
                if (result.isFailure) {
                    logger.e(
                        "AAA",
                        "Error getting data from server. Cause = ${result.exceptionOrNull()}"
                    )
                }
            }
            .map { it.toRequestResult() }

        return combine(apiRequestTableA, apiRequestTableB) { resultA, resultB ->
            val rates = mutableListOf<Rate>()
            resultA.data?.forEach { responseDTO ->
                responseDTO.rates.forEach { rateDTO ->
                    rates.add(rateDTO.toRates(responseDTO.table))
                }
            }

            resultB.data?.forEach { responseDTO ->
                responseDTO.rates.forEach { rateDTO ->
                    rates.add(rateDTO.toRates(responseDTO.table))
                }
            }

            RequestResult.Success(rates)
        }
    }

    public fun getCurrencyRateInfoFromServer(): Flow<RequestResult<List<CurrencyRate>>> {
        val apiRequest = flow {
            emit(
                api.currencyRate(
                    table = "A",
                    code = "HKD",
                    startDate = "2024-08-01",
                    endDate = "2024-09-03"
                )
            )
        }.onEach { result ->
            if (result.isSuccess) logger.d("AAA", "Success result")
        }
            .onEach { result ->
                if (result.isFailure) {
                    logger.e(
                        "AAA",
                        "Error getting data from server. Cause = ${result.exceptionOrNull()}"
                    )
                }
            }
            .map { it.toRequestResult() }

        val start =
            flowOf<RequestResult<CurrencyResponseDTO<CurrencyRateDTO>>>(RequestResult.InProgress())

        return merge(
            apiRequest,
            start
        ).map { result: RequestResult<CurrencyResponseDTO<CurrencyRateDTO>> ->
            result.map { response ->
                response.rates.map {
                    it.toCurrencyRate(
                        result.data?.currency ?: "",
                        result.data?.code ?: ""
                    )
                }
            }
        }
    }
}