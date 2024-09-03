package dev.bondar.nbp.data

import dev.bondar.common.Logger
import dev.bondar.database.NbpDataBase
import dev.bondar.nbp.data.model.Rate
import dev.bondar.nbpapi.NbpApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

public class RatesRepository(
    private val database: NbpDataBase,
    private val api: NbpApi,
    private val logger: Logger,
) {
    public fun getTableRatesFromServer(query: String): Flow<RequestResult<List<Rate>>> {
        return flowOf()
    }
}