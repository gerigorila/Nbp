package dev.bondar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.bondar.database.dao.RateDao
import dev.bondar.database.models.RateDBO

class NbpDataBase internal constructor(private val database: NbpRoomDatabase) {
    val rateDao: RateDao
        get() = database.ratesDao()
}

@Database(entities = [RateDBO::class], version = 1)
internal abstract class NbpRoomDatabase : RoomDatabase() {
    abstract fun ratesDao(): RateDao
}

fun NbpDataBase(applicationContext: Context): NbpDataBase {
    val nbpRoomDatabase =
        Room.databaseBuilder(
            checkNotNull(applicationContext.applicationContext),
            NbpRoomDatabase::class.java,
            "nbp"
        ).build()
    return NbpDataBase(nbpRoomDatabase)
}