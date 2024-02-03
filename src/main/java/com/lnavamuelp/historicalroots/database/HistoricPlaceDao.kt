package com.lnavamuelp.historicalroots.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface HistoricPlaceDao {

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        suspend fun addHistoricPlace(historicPlace: HistoricPlace)

        @Query("SELECT * FROM historicplaces WHERE id = :placeId")
        fun findPlaceById(placeId: String): HistoricPlace

        @Query("SELECT * FROM historicplaces")
        fun getAllPlaces(): List<HistoricPlace>

        @Update
        suspend fun updateHistoricPlace(historicPlace: HistoricPlace)

        @Delete
        suspend fun deletePlace(historicPlace: HistoricPlace)
    }