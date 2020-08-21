package com.challange.hilt.bdd.assertions

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers
import com.challange.hilt.R
import com.challange.hilt.bdd.assertions.RecyclerViewAssertions.assertRecyclerViewMatchersInPositions
import com.challange.hilt.bdd.assertions.RecyclerViewAssertions.assertRecyclerViewSize
import com.challange.hilt.bdd.assertions.ViewAssertions.assertDisplayingViews
import com.challange.hilt.bdd.assertions.ViewAssertions.hasDescendantWithText
import org.hamcrest.Matcher

class MainListScreenAssertions {

    fun withSuccessfullyLoadedItems() {
        try {
            //TODO: sync resources with Idling
            Thread.sleep(DELAY)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        assertRecyclerViewSize(R.id.recyclerView, 10)

        assertRecyclerViewMatchersInPositions(R.id.recyclerView, successfullyLoadedItem())
    }


    private fun successfullyLoadedItem(): List<Pair<Int, Matcher<View>>> {
        return listOf(
            Pair(0, hasDescendantWithText("2007-04-01 18:39:56")),
            Pair(1, hasDescendantWithText("2007-09-12 09:10:26")),
            Pair(2, hasDescendantWithText("2013-04-16 08:44:20")),
            Pair(3, hasDescendantWithText("2011-03-11 04:46:23")),
            Pair(4, hasDescendantWithText("2012-04-11 06:38:37")),
            Pair(5, hasDescendantWithText("2012-04-11 08:43:09")),
            Pair(6, hasDescendantWithText("2017-01-22 04:32:20")),
            Pair(7, hasDescendantWithText("2015-04-25 06:13:40")),
            Pair(8, hasDescendantWithText("2016-12-17 11:00:30")),
            Pair(9, hasDescendantWithText("2019-05-26 07:41:44"))
        )
    }

    fun withErrorState() {
        assertRecyclerViewSize(R.id.recyclerView, 0)
        assertDisplayingViews(ViewMatchers.withText(R.string.error))
    }

    companion object {
        const val DELAY = 4000.toLong()
    }
}
