package com.lnavamuelp.historicalroots.ui.screens.historicalplaces

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lnavamuelp.historicalroots.database.HistoricPlace
import com.lnavamuelp.historicalroots.repository.HistoricPlacesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoricalPlacesListViewModel @Inject constructor(private val historicPlacesRepository: HistoricPlacesRepository) : ViewModel() {


    lateinit var historicPlace: MutableLiveData<List<HistoricPlace>>

    init {
        historicPlace = MutableLiveData()
    }

    fun getRecordsObserver(): MutableLiveData<List<HistoricPlace>> {
        return historicPlace
    }


    fun getAllPlaces() {
        historicPlacesRepository.getAllPlaces()
        historicPlace.postValue(historicPlacesRepository.allPlaces.value)
    }
    fun addHistoricPlace(historicPlace: HistoricPlace) {
        historicPlacesRepository.addHistoricPlace(historicPlace)
        getAllPlaces()
    }

    fun updateHistoricPlace(historicPlace: HistoricPlace) {
        historicPlacesRepository.updatePlaceDetails(historicPlace)
        getAllPlaces()
    }

    fun deleteHistoricPlaceDetails(historicPlace: HistoricPlace) {
        historicPlacesRepository.deletePlace(historicPlace)
        getAllPlaces()
    }

    fun findPlaceById(placeId: String) {
        historicPlacesRepository.findPlaceById(placeId.toLong())
    }

    val historicPlaceList: LiveData<List<HistoricPlace>> = historicPlacesRepository.allPlaces
    val foundPlace: LiveData<HistoricPlace> = historicPlacesRepository.foundPlace

}