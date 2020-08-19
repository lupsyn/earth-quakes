package com.challange.hilt.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.challange.hilt.data.db.dao.EarthQuakeDao
import com.challange.hilt.data.db.models.EarchQuakeResponseCacheEntity
import com.challange.hilt.data.db.models.EarthQuakeEntity

@Database(
    entities = [
        EarthQuakeEntity::class,
        EarchQuakeResponseCacheEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun earthQuakeDao(): EarthQuakeDao
}