package com.lnavamuelp.historicalroots.repository

import androidx.lifecycle.MutableLiveData
import com.lnavamuelp.historicalroots.database.HistoricPlace
import com.lnavamuelp.historicalroots.database.HistoricPlaceDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HistoricPlacesRepository @Inject constructor(private val historicPlaceDao: HistoricPlaceDao) {

    val allPlaces = MutableLiveData<List<HistoricPlace>>()
    val foundPlace = MutableLiveData<HistoricPlace>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun addHistoricPlace(newHistoricPlace: HistoricPlace) {
        coroutineScope.launch(Dispatchers.IO) {
            historicPlaceDao.addHistoricPlace(newHistoricPlace)
        }
    }

    fun updatePlaceDetails(newHistoricPlace: HistoricPlace) {
        coroutineScope.launch(Dispatchers.IO) {
            historicPlaceDao.updateHistoricPlace(newHistoricPlace)
        }
    }

    fun getAllPlaces() {
        coroutineScope.launch(Dispatchers.IO) {
            allPlaces.postValue(historicPlaceDao.getAllPlaces())
        }
    }

    fun deletePlace(historicPlace: HistoricPlace) {
        coroutineScope.launch(Dispatchers.IO) {
            historicPlaceDao.deletePlace(historicPlace)
        }
    }

    fun findPlaceById(placeId: Long) {
        coroutineScope.launch(Dispatchers.IO) {
            foundPlace.postValue(historicPlaceDao.findPlaceById(placeId))
        }
    }

}