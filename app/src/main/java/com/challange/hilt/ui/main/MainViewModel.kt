package com.challange.hilt.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.challange.hilt.ui.main.Result.Success
import com.challange.hilt.ui.main.interactors.MainInteractor
import com.challange.hilt.ui.models.EarthQuakesUiModel
import com.challange.hilt.ui.utils.ErrorState
import com.challange.hilt.ui.utils.TransientUIState
import kotlinx.coroutines.launch
import timber.log.Timber
import com.challange.hilt.R

class MainViewModel @ViewModelInject constructor
    (
    private val interactor: MainInteractor
) : ViewModel() {

    private val earthQuakesLiveData = MutableLiveData<List<EarthQuakesUiModel>>()

    val earthQuakes: LiveData<List<EarthQuakesUiModel>>
        get() = earthQuakesLiveData

    private val uiStateLiveData = MutableLiveData<TransientUIState>()

    val uiState: LiveData<TransientUIState>
        get() = uiStateLiveData

    fun fetchData(toForceRefresh: Boolean = false) {
        viewModelScope.launch {
            uiStateLiveData.value = TransientUIState.LoadingUIState
            interactor.getEarthQuakes(
                formatted = true,
                north = 44.1,
                south = -9.9,
                east = -22.4,
                west = 55.2,
                toForceRefresh = toForceRefresh
            ).let { result ->
                if (result is Success<List<EarthQuakesUiModel>>) {
                    earthQuakesLiveData.value = result.data.sortedBy { it.eqid }
                    uiStateLiveData.value = TransientUIState.DisplayDataUIState

                } else if (result is Result.Error) {
                    uiStateLiveData.value = TransientUIState.ErrorUIState(
                        ErrorState.FullScreenNetworkErrorAndRetryWith(
                            { fetchData() }, R.string.error
                        )
                    )
                    Timber.e(result.exception)
                }
            }
        }
    }
}