package dev.bondar.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rates")
data class RateDBO(
    @ColumnInfo("currency") val currency: String?,
    @ColumnInfo("code") val code: String?,
    @ColumnInfo("mid") val mid: Double?,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)