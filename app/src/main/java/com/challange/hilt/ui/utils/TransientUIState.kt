package com.challange.hilt.ui.utils

sealed class TransientUIState {
    object DisplayDataUIState : TransientUIState()
    object LoadingUIState : TransientUIState()
    data class ErrorUIState(val errorState: ErrorState) : TransientUIState()

    companion object {
        fun onTransientState(
            transientUIState: TransientUIState,
            onError: (errorState: ErrorState) -> Unit = { Unit },
            onLoading: () -> Unit = { Unit },
            onDisplayData: () -> Unit = { Unit }
        ) {
            when (transientUIState) {
                is LoadingUIState -> onLoading()
                is ErrorUIState -> onError(transientUIState.errorState)
                else -> onDisplayData()
            }
        }
    }
}