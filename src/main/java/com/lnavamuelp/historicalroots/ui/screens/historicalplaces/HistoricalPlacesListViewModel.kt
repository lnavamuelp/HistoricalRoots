package com.lnavamuelp.historicalroots.ui.screens.historicalplaces

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lnavamuelp.historicalroots.database.HistoricPlace
import com.lnavamuelp.historicalroots.repository.HistoricPlacesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HistoricalPlacesListViewModel @Inject constructor(private val historicPlacesRepository: HistoricPlacesRepository) : ViewModel() {

    private val _historicPlacesList = MutableStateFlow<List<HistoricPlace>>(emptyList())
    val historicPlacesList: StateFlow<List<HistoricPlace>> get() = _historicPlacesList

    fun getAllPlaces() {
        historicPlacesRepository.getAllPlaces()
    }
    fun addPlace(historicPlace: HistoricPlace) {
        historicPlacesRepository.addHistoricPlace(historicPlace)
        getAllPlaces()
    }

    fun updatePlaceDetails(historicPlace: HistoricPlace) {
        historicPlacesRepository.updatePlaceDetails(historicPlace)
        getAllPlaces()
    }

    fun deletePlaceDetails(historicPlace: HistoricPlace) {
        historicPlacesRepository.deletePlace(historicPlace)
        getAllPlaces()
    }

    fun findPlaceById(placeId: String) {
        historicPlacesRepository.findPlaceById(placeId)
    }


    val foundPlace: LiveData<HistoricPlace> = historicPlacesRepository.foundPlace
    val historicPlaceList: LiveData<List<HistoricPlace>> = historicPlacesRepository.allPlaces
}