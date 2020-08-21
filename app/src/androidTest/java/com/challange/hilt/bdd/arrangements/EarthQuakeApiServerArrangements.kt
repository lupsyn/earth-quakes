package com.challange.hilt.bdd.arrangements

import com.challange.hilt.bdd.matchers.RecordedRequestMatchers.pathContains
import com.challange.hilt.bdd.matchers.RecordedRequestMatchers.withQueryParam
import com.challange.hilt.mockwebserver.MockResponseBuilder.Companion.withResponse
import com.challange.hilt.mockwebserver.MockResponseBuilder.Companion.withSuccessfulResponse
import com.challange.hilt.mockwebserver.MockWebServerRule
import com.challange.hilt.utils.MockResponses.errorClientTimeout
import com.challange.hilt.utils.MockResponses.stubResponse
import org.hamcrest.CoreMatchers.allOf
import java.net.HttpURLConnection

private const val PATH_REPO = "/earthquakesJSON"


class EarthQuakeApiServerArrangements(serverRule: MockWebServerRule) :
    ApiServerArrangements(serverRule) {

    // http://api.geonames.org/earthquakesJSON?formatted=true&north=44.1&south=-9.9&east=-22.4&west=55.2&username=mkoppelman

    fun returnError() {
        val requestMatcher = allOf(
            pathContains(PATH_REPO),
            withQueryParam("formatted", "true"),
            withQueryParam("north", "44.1"),
            withQueryParam("south", "-9.9"),
            withQueryParam("east", "-22.4"),
            withQueryParam("west", "55.2"),
            withQueryParam("username", "mkoppelman")
        )

        respondOn(
            requestMatcher,
            withResponse()
                .responseCode(HttpURLConnection.HTTP_CLIENT_TIMEOUT)
                .body(errorClientTimeout)
        )
    }


    fun returnsEarthQuakes() {
        val requestMatcher = allOf(
            pathContains(PATH_REPO),
            withQueryParam("formatted", "true"),
            withQueryParam("north", "44.1"),
            withQueryParam("south", "-9.9"),
            withQueryParam("east", "-22.4"),
            withQueryParam("west", "55.2"),
            withQueryParam("username", "mkoppelman")
        )

        respondOn(requestMatcher, withSuccessfulResponse().body(stubResponse))
    }
}