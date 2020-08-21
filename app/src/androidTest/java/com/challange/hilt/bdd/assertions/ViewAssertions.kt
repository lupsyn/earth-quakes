package com.challange.hilt.bdd.assertions

import android.view.View
import androidx.annotation.IdRes
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*

object ViewAssertions {

    fun assertDisplayingView(viewMatcher: Matcher<View>) {
        // Wrapping viewMatcher with isDisplayed() covers for cases when there're multiple views that matched in the view hierarchy
        onView(allOf(viewMatcher, isDisplayed())).check(matches(isDisplayed()))
    }

    fun assertDisplayingViews(vararg viewMatchers: Matcher<View>) {
        viewMatchers.forEach {
            onView(it).check(matches(isDisplayed()))
        }
    }

    fun assertNotDisplayingView(viewMatcher: Matcher<View>) {
        onView(isRoot()).check(matches(not(hasDescendant(allOf(viewMatcher, isDisplayed())))))
    }

    fun hasDescendantWithText(text: String): Matcher<View> = hasDescendant(withText(text))

    fun assertDisplayingText(@IdRes id: Int, text: String) {
        Espresso.onView(withId(id)).check(matches(withText(text)))
    }

    fun assertContainsText(@IdRes id: Int, text: String) {
        Espresso.onView(withId(id)).check(matches(withText(containsString(text))))
    }
}
