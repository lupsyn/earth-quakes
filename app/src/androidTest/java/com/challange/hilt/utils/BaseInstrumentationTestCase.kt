package com.challange.hilt.utils

import androidx.annotation.CallSuper
import com.challange.hilt.bdd.Given
import com.challange.hilt.bdd.Then
import com.challange.hilt.bdd.When
import com.challange.hilt.data.db.AppDatabase
import com.challange.hilt.mockwebserver.MockWebServerRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

/**
 * Base class for all UI tests.
 *
 * Prepare the app to run BDD-style tests and handle configuration needed before running each test.
 */
@HiltAndroidTest
abstract class BaseInstrumentationTestCase {

    @get:Rule
    val serverRule: MockWebServerRule = MockWebServerRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var db: AppDatabase

    lateinit var given: Given
    lateinit var `when`: When
    lateinit var then: Then

    @Before
    @CallSuper
    open fun setUp() {
        hiltRule.inject()

        db.clearAllTables()
        given = Given(serverRule, db)
        `when` = When()
        then = Then()
    }

    @After
    fun tearDown() = serverRule.shutdownServer()
}