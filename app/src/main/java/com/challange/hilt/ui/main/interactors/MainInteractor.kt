package com.challange.hilt.ui.main.interactors

import com.challange.hilt.ui.main.Result
import com.challange.hilt.ui.models.EarthQuakesUiModel

interface MainInteractor {
    suspend fun getEarthQuakes(
        formatted: Boolean,
        north: Double,
        south: Double,
        east: Double,
        west: Double
    ): Result<List<EarthQuakesUiModel>>
}