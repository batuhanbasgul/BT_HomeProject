package com.example.bt_homeproject.db.data_model.geo_position

data class TimeZone(
    val Code: String,
    val GmtOffset: Double,
    val IsDaylightSaving: Boolean,
    val Name: String,
    val NextOffsetChange: Any
)