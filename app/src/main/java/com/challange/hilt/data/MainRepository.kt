package com.challange.hilt.data

import com.challange.hilt.data.network.models.EarthQuakesResponse
import com.challange.hilt.ui.main.Result
import retrofit2.HttpException
import retrofit2.Response
import java.lang.IllegalStateException

interface MainRepository {
    suspend fun getEarthQuakes(
        formatted: Boolean,
        north: Double,
        south: Double,
        east: Double,
        west: Double
    ): Result<EarthQuakesResponse>

    suspend fun <T> getResult(call: suspend () -> Response<T>): Result<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return Result.Success(body)
                }
            } else
                return Result.Error(Exception(response.errorBody().toString()))
        } catch (throwable: Throwable) {
            return Result.Error(Exception(throwable))
        }
        return Result.Error(IllegalStateException("Error on retrieving data from network"))
    }
}