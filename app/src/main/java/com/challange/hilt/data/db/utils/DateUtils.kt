package com.challange.hilt.data.db.utils

import java.sql.Timestamp
import javax.inject.Inject

class DateUtils @Inject constructor() {
    fun getTimestamp(): Long = Timestamp(System.currentTimeMillis()).time
}