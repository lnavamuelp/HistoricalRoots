package com.lnavamuelp.historicalroots.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.lnavamuelp.historicalroots.database.HistoricPlace
import com.lnavamuelp.historicalroots.database.HistoricPlaceDao
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HistoricPlacesRepository @Inject constructor(private val historicPlaceDao: HistoricPlaceDao) {

    val allPlaces = MutableLiveData<List<HistoricPlace>>()
    val foundPlace = MutableLiveData<HistoricPlace?>()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        // Handle exceptions here, for example, logging or updating UI with error message
        throwable.printStackTrace()
        // You can post an empty list or handle errors as per your requirement
        allPlaces.postValue(emptyList())
        foundPlace.postValue(null)
    }

    fun addHistoricPlace(newHistoricPlace: HistoricPlace) {
        coroutineScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            historicPlaceDao.addHistoricPlace(newHistoricPlace)
        }
    }

    fun updatePlaceDetails(newHistoricPlace: HistoricPlace) {
        coroutineScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            historicPlaceDao.updateHistoricPlace(newHistoricPlace)
        }
    }

    fun getAllPlaces() {
        coroutineScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            allPlaces.postValue(historicPlaceDao.getAllPlaces())
        }
    }

    fun deletePlace(historicPlace: HistoricPlace) {
        coroutineScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            historicPlaceDao.deletePlace(historicPlace)
        }
    }

    fun findPlaceById(placeId: Long) {
        Log.d("FIND PLACE REPOSITORY", "$placeId")
        coroutineScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            foundPlace.postValue(historicPlaceDao.findPlaceById(placeId))
        }
    }
}
