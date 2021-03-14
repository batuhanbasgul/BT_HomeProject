package com.example.bt_homeproject.db.data_model.forecast

data class Night(
    val HasPrecipitation: Boolean,
    val Icon: Int,
    val IconPhrase: String,
    val PrecipitationIntensity: String?,
    val PrecipitationType: String?
)