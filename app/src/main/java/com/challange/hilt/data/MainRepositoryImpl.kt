package com.challange.hilt.data

import com.challange.hilt.data.network.Api
import com.challange.hilt.data.network.models.EarthQuakesResponse
import com.challange.hilt.ui.main.Result
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val api: Api,
    private val userName: String
) : MainRepository {
    override suspend fun getEarthQuakes(
        formatted: Boolean,
        north: Double,
        south: Double,
        east: Double,
        west: Double
    ): Result<EarthQuakesResponse> = getResult {
        api.getEarthQuakes(
            formatted = formatted,
            north = north,
            south = south,
            east = east,
            west = west,
            username = userName
        )
    }
}

