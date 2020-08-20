package com.challange.hilt.data.db.mapper

import com.challange.hilt.data.db.models.EarthQuakeEntity
import com.challange.hilt.data.network.models.EarthQuake
import javax.inject.Inject

class EarthQuakeEntityMapper @Inject constructor() {
    fun toUiModels(entity: EarthQuakeEntity): EarthQuake {
        return EarthQuake(
            datetime = entity.datetime,
            depth = entity.depth,
            lng = entity.lng,
            lat = entity.lat,
            src = entity.src,
            eqid = entity.eqid,
            magnitude = entity.magnitude
        )
    }

    fun toEntity(hashCode: Int, networkResponse: EarthQuake): EarthQuakeEntity {
        return EarthQuakeEntity(
            datetime = networkResponse.datetime,
            depth = networkResponse.depth,
            lng = networkResponse.lng,
            lat = networkResponse.lat,
            src = networkResponse.src,
            eqid = networkResponse.eqid,
            magnitude = networkResponse.magnitude,
            requestHash = hashCode
        )
    }
}