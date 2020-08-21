package com.challange.hilt.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.challange.hilt.TestBase
import com.challange.hilt.ui.main.interactors.MainInteractor
import com.challange.hilt.ui.models.EarthQuakesUiModel
import com.challange.hilt.ui.utils.TransientUIState
import com.challange.hilt.util.MainCoroutineRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock

class MainViewModelTest : TestBase() {
    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var uiModel: EarthQuakesUiModel

    private val listUiModels: List<EarthQuakesUiModel> by lazy { listOf(uiModel) }

    @Mock
    lateinit var sortedList: List<EarthQuakesUiModel>

    @Mock
    private lateinit var mockUiObserved: Observer<TransientUIState>

    @Mock
    private lateinit var earthQuakesObserved: Observer<List<EarthQuakesUiModel>>

    @Mock
    private lateinit var interactor: MainInteractor


    private lateinit var underTest: MainViewModel

    @Before
    fun setup() {
        underTest = MainViewModel(interactor)
            .apply {
                uiState.observeForever(mockUiObserved)
                earthQuakes.observeForever(earthQuakesObserved)
            }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should retrieve data once view model is init and change the Ui state correctly`() {

        runBlockingTest {
            whenever(
                interactor.getEarthQuakes(
                    formatted = true,
                    north = 44.1,
                    south = -9.9,
                    east = -22.4,
                    west = 55.2,
                    toForceRefresh = false
                )
            ).thenReturn(Result.Success(listUiModels))

            underTest.fetchData(false)
        }

        val inOrder = inOrder(mockUiObserved)
        val inOrderUiModels = inOrder(earthQuakesObserved)

        inOrder.verify(mockUiObserved).onChanged(TransientUIState.LoadingUIState)
        inOrder.verify(mockUiObserved).onChanged(TransientUIState.DisplayDataUIState)

        inOrderUiModels.verify(earthQuakesObserved).onChanged(listUiModels.sortedBy { it.eqid })
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should emit error if something bad is happening`() {
        val networkError = Result.Error(
            Exception("NetworkError")
        )
        val somethingCallback = mock<() -> Unit>()

        runBlockingTest {
            whenever(
                interactor.getEarthQuakes(
                    formatted = true,
                    north = 44.1,
                    south = -9.9,
                    east = -22.4,
                    west = 55.2,
                    toForceRefresh = false
                )
            ).thenReturn(networkError)

            underTest.fetchData(false)
        }

        val inOrder = inOrder(mockUiObserved)

        inOrder.verify(mockUiObserved).onChanged(TransientUIState.LoadingUIState)
        inOrder.verify(mockUiObserved).onChanged(any<TransientUIState.ErrorUIState>())
    }
}

