package com.challange.hilt.data.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EarthQuakesResponse(
    @Json(name = "earthquakes") val earthQuakes: List<EarthQuake>
)

@JsonClass(generateAdapter = true)
data class EarthQuake(
    @Json(name = "datetime") val datetime: String,
    @Json(name = "depth") val depth: Double,
    @Json(name = "lng") val lng: Double,
    @Json(name = "src") val src: String,
    @Json(name = "eqid") val eqid: String,
    @Json(name = "magnitude") val magnitude: Double,
    @Json(name = "lat") val lat: Double
)