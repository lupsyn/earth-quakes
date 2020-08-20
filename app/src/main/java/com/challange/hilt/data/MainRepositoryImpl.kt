package com.challange.hilt.data

import com.challange.hilt.data.db.dao.EarthQuakeDao
import com.challange.hilt.data.db.dao.EarthQuakeResponseConfigDao
import com.challange.hilt.data.db.mapper.EarthQuakeEntityMapper
import com.challange.hilt.data.db.models.EarchQuakeResponseCacheEntity
import com.challange.hilt.data.db.utils.DateUtils
import com.challange.hilt.data.network.Api
import com.challange.hilt.data.network.models.EarthQuakesResponse
import com.challange.hilt.ui.main.Result
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val api: Api,
    private val userName: String,
    private val mapper: EarthQuakeEntityMapper,
    private val earthQuakeDao: EarthQuakeDao,
    private val earthQuakesResponseDao: EarthQuakeResponseConfigDao,
    private val dateUtils: DateUtils

) : MainRepository {
    override suspend fun getEarthQuakes(
        formatted: Boolean,
        north: Double,
        south: Double,
        east: Double,
        west: Double,
        toForceRefresh: Boolean
    ): Result<EarthQuakesResponse> {
        val hashCode = (formatted.toString() + north + south + east + west).hashCode()

        if (toForceRefresh) {
            earthQuakesResponseDao.clearAll()
        }

        val dbResponse = earthQuakeDao.validResultCardEntities(
            earthQuakesResponseDao,
            EXPIRY_TIME_STANDARD,
            hashCode
        )

        return if (dbResponse.isNotEmpty()) {
            val earthQuakes = dbResponse.map { mapper.toUiModels(it) }
            Result.Success(EarthQuakesResponse(earthQuakes))
        } else {
            val networkResponse = getResult {
                api.getEarthQuakes(
                    formatted = formatted,
                    north = north,
                    south = south,
                    east = east,
                    west = west,
                    username = userName
                )
            }
            when (networkResponse) {
                is Result.Success -> {
                    val earthQuakesEntities = networkResponse.data.earthquakes.map {
                        mapper.toEntity(hashCode, it)
                    }
                    val earthQuakeConfiguration = EarchQuakeResponseCacheEntity(
                        hashCode,
                        dateUtils.getTimestamp()
                    )

                    earthQuakesResponseDao.insertConfiguration(earthQuakeConfiguration)
                    earthQuakeDao.insertEntities(earthQuakesEntities)

                    return networkResponse
                }
                else -> return networkResponse
            }
        }
    }

    companion object {
        private const val SECONDS_IN_MINUTE = 60
        private const val MILLISECONDS_IN_SECOND = 1000
        private const val MINUTE_TO_MILLISECONDS = SECONDS_IN_MINUTE * MILLISECONDS_IN_SECOND
        const val EXPIRY_TIME_STANDARD = (30 * MINUTE_TO_MILLISECONDS).toLong()
    }
}