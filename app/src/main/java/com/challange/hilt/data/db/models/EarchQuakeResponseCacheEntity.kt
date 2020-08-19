package com.challange.hilt.data.db.models

import androidx.room.*
import com.challange.hilt.data.db.models.EarchQuakeResponseCacheEntity.Companion.REQUEST_HASH

const val EARTH_QUAKE_RESPONSE_TABLE_NAME = "earth_quake_response_table"

@Entity(
    tableName = EARTH_QUAKE_RESPONSE_TABLE_NAME,
    indices = [(Index(value = [REQUEST_HASH], unique = true))]
)
data class EarchQuakeResponseCacheEntity(
    @ColumnInfo(name = REQUEST_HASH)
    @PrimaryKey
    val requestDataHash: Int,

    @ColumnInfo(name = TIMESTAMP)
    val timestamp: Double
) {
    @Ignore
    constructor() : this(0, 0f.toDouble())

    companion object {
        const val REQUEST_HASH = "requestDataHash"
        const val TIMESTAMP = "timestamp"
    }
}