package com.challange.hilt.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.challange.hilt.MainCoroutineRule
import com.challange.hilt.TestBase
import com.challange.hilt.data.MainRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
    lateinit var repoMock: MainRepository

    lateinit var underTest: MainViewModel


    @Test
    fun `should retrieve data once view model is init`() {

    }

    @Test
    fun `should emit error if something bad is happening`() {

    }

    @Test
    fun `should load once we are retrieving data`() {

    }
}

