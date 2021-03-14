package com.example.bt_homeproject.db.data_model.geo_position

data class GeoPositionData(
        val AdministrativeArea: AdministrativeArea,
        val Country: Country,
        val DataSets: List<String>,
        val EnglishName: String,
        val GeoPosition: GeoPosition,
        val IsAlias: Boolean,
        val Key: String,
        val LocalizedName: String,
        val PrimaryPostalCode: String,
        val Rank: Int,
        val Region: Region,
        val SupplementalAdminAreas: List<Any>,
        val TimeZone: TimeZone,
        val Type: String,
        val Version: Int
)