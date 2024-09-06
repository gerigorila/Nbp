package dev.bondar.nbp.data

import android.graphics.Color
import dev.bondar.common.Logger
import dev.bondar.nbp.data.model.CurrencyRate
import dev.bondar.nbp.data.model.Rate
import dev.bondar.nbpapi.NbpApi
import dev.bondar.nbpapi.models.CurrencyRateDTO
import dev.bondar.nbpapi.models.CurrencyResponseDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.math.abs

public class RatesRepository @Inject constructor(
    private val api: NbpApi,
    private val logger: Logger,
) {
    public fun getTableRatesFromServer(): Flow<RequestResult<List<Rate>>> {
        val apiRequestTableA = flow { emit(api.table(TABLE_A)) }
            .onEach { result ->
                if (result.isSuccess)
                    logger.d(RatesRepository::class.simpleName.toString(), "Success result")
            }
            .onEach { result ->
                if (result.isFailure) {
                    logger.e(
                        RatesRepository::class.simpleName.toString(),
                        "Error getting data from server. Cause = ${result.exceptionOrNull()}"
                    )
                }
            }
            .map { it.toRequestResult() }

        val apiRequestTableB = flow { emit(api.table(TABLE_B)) }
            .onEach { result ->
                if (result.isSuccess)
                    logger.d(RatesRepository::class.simpleName.toString(), "Success result")
            }
            .onEach { result ->
                if (result.isFailure) {
                    logger.e(
                        RatesRepository::class.simpleName.toString(),
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

    public fun getCurrencyRateInfoFromServer(table: String, code: String): Flow<RequestResult<List<CurrencyRate>>> {
        val apiRequest = flow {
            emit(
                api.currencyRate(
                    table = table,
                    code = code,
                    startDate = LocalDate.now().minusDays(TWO_WEEKS).format(DateTimeFormatter.ofPattern(YYYY_MM_DD)),
                    endDate = LocalDate.now().minusDays(ONE_DAY).format(DateTimeFormatter.ofPattern(YYYY_MM_DD))
                )
            )
        }.onEach { result ->
            if (result.isSuccess)
                logger.d(RatesRepository::class.simpleName.toString(), "Success result")
        }
            .onEach { result ->
                if (result.isFailure) {
                    logger.e(
                        RatesRepository::class.simpleName.toString(),
                        "Error getting data from server. Cause = ${result.exceptionOrNull()}"
                    )
                }
            }
            .map { it.toRequestResult() }

        return apiRequest.map { result: RequestResult<CurrencyResponseDTO<CurrencyRateDTO>> ->
            result.map { response ->
                response.rates.reversed().map {
                    it.toCurrencyRate(
                        currency = response.currency,
                        code = response.code,
                        color = checkCurrencyTrend(response.rates.last().mid, it.mid)
                    )
                }
            }
        }
    }
}

private fun checkCurrencyTrend(firstMid: Double?, currentMid: Double?): Int {
    return if (firstMid != null && currentMid != null && abs(firstMid - currentMid) < (firstMid * PERCENTAGE))
        Color.LTGRAY
    else
        Color.RED
}

private const val PERCENTAGE = 0.01
private const val TABLE_A = "A"
private const val TABLE_B = "B"
private const val ONE_DAY = 1L
private const val TWO_WEEKS = 14L
private const val YYYY_MM_DD = "yyyy-MM-dd"