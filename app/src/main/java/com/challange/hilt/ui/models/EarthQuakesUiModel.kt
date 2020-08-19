package com.challange.hilt.ui.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EarthQuakesUiModel(
    val datetime: String,
    val depth: Double,
    val lng: Double,
    val lat: Double,
    val src: String,
    val eqid: String,
    val magnitude: Double,
    val highlightColor: Int
) : Parcelable