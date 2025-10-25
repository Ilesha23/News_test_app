package com.ilesha.newstestapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.ilesha.newstestapp.data.local.dao.ArticleDao
import com.ilesha.newstestapp.data.local.dao.RemoteKeysDao
import com.ilesha.newstestapp.data.local.entity.ArticleEntity
import com.ilesha.newstestapp.data.local.entity.RemoteKeysEntity
import java.time.Instant

@Database(
    entities = [ArticleEntity::class, RemoteKeysEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao
    abstract fun remoteKeysDao(): RemoteKeysDao

}

class DateConverter {

    @TypeConverter
    fun fromTimeStampToDate(timestamp: Long?): Instant? {
        return timestamp?.let {
            Instant.ofEpochMilli(it)
        }
    }

    @TypeConverter
    fun fromDateToMillis(date: Instant?): Long? {
        return date?.toEpochMilli()
    }

}