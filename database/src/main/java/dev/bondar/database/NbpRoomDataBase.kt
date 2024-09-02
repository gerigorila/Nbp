package dev.bondar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.bondar.database.dao.RateDao
import dev.bondar.database.models.RateDBO

class NbpDataBase internal constructor(private val database: NewsRoomDatabase) {
    val articlesDao: RateDao
        get() = database.ratesDao()
}

@Database(entities = [RateDBO::class], version = 1)
internal abstract class NewsRoomDatabase : RoomDatabase() {
    abstract fun ratesDao(): RateDao
}

fun NbpDataBase(applicationContext: Context): NbpDataBase {
    val newsRoomDatabase =
        Room.databaseBuilder(
            checkNotNull(applicationContext.applicationContext),
            NewsRoomDatabase::class.java,
            "nbp"
        ).build()
    return NbpDataBase(newsRoomDatabase)
}