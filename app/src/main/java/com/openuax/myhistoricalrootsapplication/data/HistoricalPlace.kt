package com.openuax.myhistoricalrootsapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "historicalplaces")
data class HistoricalPlace(
    @PrimaryKey @ColumnInfo(name = "id") val placeId: String,
    val name: String,
    val short_description: String,
    val long_description: String,
    val imageUrl: String,
    val location: String,
    val category: String,
    val latitude: Double,
    val longitude: Double
)
