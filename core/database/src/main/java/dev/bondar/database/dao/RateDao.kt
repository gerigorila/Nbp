package dev.bondar.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dev.bondar.database.models.RateDBO
import kotlinx.coroutines.flow.Flow

@Dao
interface RateDao {
    @Query("SELECT * FROM rates")
    suspend fun getAll(): List<RateDBO>

    @Query("SELECT * FROM rates")
    fun observeAll(): Flow<List<RateDBO>>

    @Insert
    suspend fun insert(articles: List<RateDBO>)

    @Delete
    suspend fun remove(articles: List<RateDBO>)

    @Query("DELETE FROM rates")
    suspend fun clean()
}