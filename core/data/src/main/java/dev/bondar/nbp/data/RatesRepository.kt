package dev.bondar.nbp.data

import dev.bondar.common.Logger
import dev.bondar.nbp.data.model.CurrencyRate
import dev.bondar.nbp.data.model.Rate
import dev.bondar.nbpapi.NbpApi
import dev.bondar.nbpapi.models.CurrencyRateDTO
import dev.bondar.nbpapi.models.CurrencyResponseDTO
import dev.bondar.nbpapi.models.RateDTO
import dev.bondar.nbpapi.models.ResponseDTO
import kotlinx.coroutines.flow.Flow
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
        val apiRequest = flow { emit(api.table(query = query)) }
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

        val start = flowOf<RequestResult<List<ResponseDTO<RateDTO>>>>(RequestResult.InProgress())

        return merge(apiRequest, start)
            .map { result: RequestResult<List<ResponseDTO<RateDTO>>> ->
                result.map { response ->
                    response.first().rates.map { it.toRates() }
                }
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

        val start = flowOf<RequestResult<CurrencyResponseDTO<CurrencyRateDTO>>>(RequestResult.InProgress())

        return merge(apiRequest, start).map { result: RequestResult<CurrencyResponseDTO<CurrencyRateDTO>> ->
            result.map { response ->
                response.rates.map { it.toCurrencyRate(result.data?.currency?: "", result.data?.code?: "") }
            }
        }
    }
}