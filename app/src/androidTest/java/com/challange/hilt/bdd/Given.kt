package com.challange.hilt.bdd

import com.challange.hilt.bdd.arrangements.DatabaseArrangements
import com.challange.hilt.bdd.arrangements.EarthQuakeApiServerArrangements
import com.challange.hilt.bdd.arrangements.UserArrangements
import com.challange.hilt.data.db.AppDatabase
import com.challange.hilt.mockwebserver.MockWebServerRule


class Given(serverRule: MockWebServerRule, db: AppDatabase) {


    val gitHubServer = EarthQuakeApiServerArrangements(serverRule)
    val database = DatabaseArrangements(db)
    val user = UserArrangements()
}