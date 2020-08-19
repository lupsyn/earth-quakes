package com.challange.hilt.data.network

import com.challange.hilt.data.network.models.EarthQuakesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    //Example of a call : http://api.geonames.org/earthquakesJSON?formatted=true&north=44.1&south=-9.9&east=-22.4&west=55.2&username=mkoppelman

    @GET("earthquakesJSON?")
    suspend fun getEarthQuakes(
        @Query(FORMATTED) formatted: Boolean,
        @Query(COORDINATES_NORTH) north: Double,
        @Query(COORDINATES_SOUTH) south: Double,
        @Query(COORDINATES_EAST) east: Double,
        @Query(COORDINATES_WEST) west: Double,
        @Query(USERNAME) username: String
    ): Response<EarthQuakesResponse>

    companion object {
        const val FORMATTED = "formatted"
        const val COORDINATES_NORTH = "north"
        const val COORDINATES_SOUTH = "south"
        const val COORDINATES_EAST = "east"
        const val COORDINATES_WEST = "west"
        const val USERNAME = "username"
    }
}