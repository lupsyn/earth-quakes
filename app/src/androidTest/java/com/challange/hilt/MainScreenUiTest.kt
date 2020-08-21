package com.challange.hilt

import com.challange.hilt.utils.BaseInstrumentationTestCase
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@HiltAndroidTest
class MainScreenUiTest : BaseInstrumentationTestCase() {

    @Test
    fun willDisplayFirstPaginatedListCorrectly() {
        given.gitHubServer.returnsEarthQuakes()

        `when`.user.launchesTheApp()

        then.user.sees.mainListScreenAssertions.withSuccessfullyLoadedItems()
    }


    @Test
    fun willDisplayErrorCorrectly() {
        given.gitHubServer.returnError()

        `when`.user.launchesTheApp()

        then.user.sees.mainListScreenAssertions.withErrorState()
    }
}
