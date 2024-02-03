package com.lnavamuelp.historicalroots.database

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "historicplaces")
data class HistoricPlace(@PrimaryKey @NonNull @ColumnInfo(name = "id") val placeId: String,
                         @ColumnInfo(name = "name") val name: String,
                         @ColumnInfo(name = "short_description") val short_description: String,
                         @ColumnInfo(name = "long_description") val long_description: String,
                         @ColumnInfo(name = "imageUrl") val imageUrl: String,
                         @ColumnInfo(name = "location") val location: String,
                         @ColumnInfo(name = "category") val category: String,
                         @ColumnInfo(name = "latitude") val latitude: Double,
                         @ColumnInfo(name = "longitude") val longitude: Double) : Parcelable
