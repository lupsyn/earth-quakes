package com.challange.hilt.ui.main.interactors

import com.challange.hilt.data.MainRepository
import com.challange.hilt.ui.main.Result
import com.challange.hilt.ui.main.Result.Error
import com.challange.hilt.ui.main.Result.Success
import com.challange.hilt.ui.main.mappers.EarthQuakeMapper
import com.challange.hilt.ui.models.EarthQuakesUiModel
import com.challange.hilt.util.wrapEspressoIdlingResource
import javax.inject.Inject

class MainInteractorImpl @Inject constructor(
    private val mainRepository: MainRepository,
    private val mapper: EarthQuakeMapper
) : MainInteractor {
    override suspend fun getEarthQuakes(
        formatted: Boolean,
        north: Double,
        south: Double,
        east: Double,
        west: Double
    ): Result<List<EarthQuakesUiModel>> {
        wrapEspressoIdlingResource {
            val response = mainRepository.getEarthQuakes(
                formatted = formatted,
                north = north,
                east = east,
                south = south,
                west = west
            )
            return when (response) {
                is Success -> Success(response.data.earthQuakes.map { mapper.toUiModels(it) })
                is Error -> Error(response.exception)
                else -> Error(IllegalStateException("Invalid state"))
            }
        }
    }
}