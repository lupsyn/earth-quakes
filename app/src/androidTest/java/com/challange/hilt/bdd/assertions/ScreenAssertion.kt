package com.challange.hilt.bdd.assertions

/**
 * Wrapper for various screen assertions.
 *
 * Typical usage is:
 * _then.user.sees.someScreenIsDisplayed()_
 */
class ScreenAssertion {

    val mainListScreenAssertions by lazy { MainListScreenAssertions() }

}
