package com.lnavamuelp.historicalroots.repository

import androidx.lifecycle.MutableLiveData
import com.lnavamuelp.historicalroots.database.HistoricPlace
import com.lnavamuelp.historicalroots.database.HistoricPlaceDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoricPlacesRepository(private val HistoricPlaceDao: HistoricPlaceDao) {

    val allPlaces = MutableLiveData<List<HistoricPlace>>()
    val foundPlace = MutableLiveData<HistoricPlace>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun addHistoricPlace(newHistoricPlace: HistoricPlace) {
        coroutineScope.launch(Dispatchers.IO) {
            HistoricPlaceDao.addHistoricPlace(newHistoricPlace)
        }
    }

    fun updatePlaceDetails(newHistoricPlace: HistoricPlace) {
        coroutineScope.launch(Dispatchers.IO) {
            HistoricPlaceDao.updateHistoricPlace(newHistoricPlace)
        }
    }

    fun getAllPlaces() {
        coroutineScope.launch(Dispatchers.IO) {
            allPlaces.postValue(HistoricPlaceDao.getAllPlaces())
        }
    }

    fun deletePlace(historicPlace: HistoricPlace) {
        coroutineScope.launch(Dispatchers.IO) {
            HistoricPlaceDao.deletePlace(historicPlace)
        }
    }

    fun findPlaceById(placeId: String) {
        coroutineScope.launch(Dispatchers.IO) {
            foundPlace.postValue(HistoricPlaceDao.findPlaceById(placeId))
        }
    }

}