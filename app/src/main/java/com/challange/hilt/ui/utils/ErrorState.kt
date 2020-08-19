package com.challange.hilt.ui.utils

import androidx.annotation.StringRes

sealed class ErrorState(@StringRes val errorMessage: Int) {
    class FullScreenNetworkErrorAndRetryWith(val retry: () -> Unit, @StringRes val stringId: Int) :
        ErrorState(0)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ErrorState) return false

        if (errorMessage != other.errorMessage) return false

        return true
    }

    override fun hashCode(): Int {
        return errorMessage
    }
}