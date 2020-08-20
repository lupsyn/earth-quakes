package com.challange.hilt.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.challange.hilt.data.db.models.EARTH_QUAKE_RESPONSE_TABLE_NAME
import com.challange.hilt.data.db.models.EarchQuakeResponseCacheEntity

@Dao
interface EarthQuakeResponseConfigDao {
    companion object {
        const val SELECT_ALL_BY_DEFAULT_REQUEST =
            "SELECT * FROM $EARTH_QUAKE_RESPONSE_TABLE_NAME " +
                    " WHERE ${EarchQuakeResponseCacheEntity.REQUEST_HASH} LIKE :defaultRequestHash"

        const val DELETE_EXPIRED_ENTRIES =
            "DELETE FROM $EARTH_QUAKE_RESPONSE_TABLE_NAME" +
                    " WHERE ${EarchQuakeResponseCacheEntity.TIMESTAMP} < :expiredTimestampRange "
    }

    @Query(value = SELECT_ALL_BY_DEFAULT_REQUEST)
    suspend fun getConfigurationForHash(defaultRequestHash: Int): EarchQuakeResponseCacheEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConfiguration(config: EarchQuakeResponseCacheEntity)

    @Query(value = DELETE_EXPIRED_ENTRIES)
    suspend fun deleteExpiredEntries(expiredTimestampRange: Long)
}