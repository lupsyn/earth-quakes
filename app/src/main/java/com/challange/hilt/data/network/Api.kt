package com.challange.hilt.data.network

import com.challange.hilt.data.network.models.EarthQuakesResponse
import retrofit2.Response

interface Api {

    suspend fun getEarthQuakes(
        formatted: Boolean,
        north: Double,
        south: Double,
        east: Double,
        west: Double,
        username: String
    ): Response<EarthQuakesResponse>
}