package com.challange.hilt.ui.main.mappers

import com.challange.hilt.R
import com.challange.hilt.data.network.models.EarthQuake
import com.challange.hilt.ui.models.EarthQuakesUiModel
import javax.inject.Inject

class EarthQuakeMapper @Inject constructor() {
    fun toUiModels(networkModel: EarthQuake): EarthQuakesUiModel {
        return EarthQuakesUiModel(
            datetime = networkModel.datetime,
            depth = networkModel.depth,
            lng = networkModel.lng,
            lat = networkModel.lat,
            src = networkModel.src,
            eqid = networkModel.eqid,
            magnitude = networkModel.magnitude,
            highlightColor = if (networkModel.magnitude >= 8) R.color.colorHighlightMagnitude else R.color.colorHighlightNormal
        )
    }
}