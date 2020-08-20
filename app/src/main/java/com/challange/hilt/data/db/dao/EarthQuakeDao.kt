package com.challange.hilt.data.db.dao

import androidx.room.*
import com.challange.hilt.data.db.models.EARTH_QUAKE_TABLE_NAME
import com.challange.hilt.data.db.models.EarthQuakeEntity
import com.challange.hilt.data.db.models.EarthQuakeEntity.Companion.REQUEST_HASH

@Dao
interface EarthQuakeDao {
    companion object {
        const val SELECT_ALL_BY_DEFAULT_REQUEST =
            "SELECT * FROM $EARTH_QUAKE_TABLE_NAME  WHERE $REQUEST_HASH LIKE :defaultRequestHash"
    }


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntities(earthQuakes: List<EarthQuakeEntity>)

    @Query(value = SELECT_ALL_BY_DEFAULT_REQUEST)
    suspend fun getResultCardEntities(defaultRequestHash: Int): List<EarthQuakeEntity>

    @Transaction
    suspend fun validResultCardEntities(
        configDao: EarthQuakeResponseConfigDao,
        expiryTime: Long,
        defaultRequestHash: Int
    ): List<EarthQuakeEntity> {

        configDao.deleteExpiredEntries(System.currentTimeMillis() - expiryTime)
        return getResultCardEntities(defaultRequestHash)
    }
}