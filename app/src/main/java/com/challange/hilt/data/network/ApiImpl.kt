package com.challange.hilt.data.network

import com.challange.hilt.data.network.models.EarthQuakesResponse
import retrofit2.Response
import javax.inject.Inject

class ApiImpl @Inject constructor(private val apiService: ApiService) : Api {

    override suspend fun getEarthQuakes(
        formatted: Boolean,
        north: Double,
        south: Double,
        east: Double,
        west: Double,
        username: String
    ): Response<EarthQuakesResponse> =

        apiService.getEarthQuakes(
            formatted = formatted,
            north = north,
            south = south,
            east = east,
            west = west,
            username = username
        )

}