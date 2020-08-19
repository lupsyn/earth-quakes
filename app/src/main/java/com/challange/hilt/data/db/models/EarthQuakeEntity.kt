package com.challange.hilt.data.db.models

import androidx.room.*

const val EARTH_QUAKE_TABLE_NAME = "earth_quake_table"

@Entity(
    tableName = EARTH_QUAKE_TABLE_NAME,
    indices = [(Index(value = [EarthQuakeEntity.REQUEST_HASH]))],
    foreignKeys = [
        ForeignKey(
            entity = EarchQuakeResponseCacheEntity::class,
            parentColumns = [EarchQuakeResponseCacheEntity.REQUEST_HASH],
            childColumns = [EarthQuakeEntity.REQUEST_HASH],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class EarthQuakeEntity(
    @PrimaryKey
    @ColumnInfo(name = EG_ID)
    val eqid: String,

    @ColumnInfo(name = DATE_TIME)
    val datetime: String,

    @ColumnInfo(name = DEPTH)
    val depth: Double,

    @ColumnInfo(name = LNG)
    val lng: Double,

    @ColumnInfo(name = LAT)
    val lat: Double,

    @ColumnInfo(name = SRC)
    val src: String,

    @ColumnInfo(name = MAGNITUDE)
    val magnitude: Double,

    @ColumnInfo(name = REQUEST_HASH)
    val requestHash: Int
) {
    @Ignore
    constructor() : this("", "", 0.toDouble(), 0.toDouble(), 0.toDouble(), "", 0.toDouble(), 0)

    companion object {
        const val EG_ID = "eqid"
        const val DATE_TIME = "dateTime"
        const val DEPTH = "depth"
        const val LNG = "lng"
        const val LAT = "lat"
        const val SRC = "src"
        const val MAGNITUDE = "magnitude"
        const val REQUEST_HASH = "request_hash"
    }
}

