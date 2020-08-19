package com.challange.hilt.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.challange.hilt.data.db.models.EarthQuakeEntity

@Dao
interface EarthQuakeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntities(earthQuakes: List<EarthQuakeEntity>)
}