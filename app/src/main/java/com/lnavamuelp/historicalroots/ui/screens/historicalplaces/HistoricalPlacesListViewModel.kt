package com.lnavamuelp.historicalroots.ui.screens.historicalplaces

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lnavamuelp.historicalroots.database.HistoricPlace
import com.lnavamuelp.historicalroots.repository.HistoricPlacesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoricalPlacesListViewModel @Inject constructor(private val historicPlacesRepository: HistoricPlacesRepository) : ViewModel() {

    fun getAllPlaces() {
        historicPlacesRepository.getAllPlaces()
    }

    fun addHistoricPlace(historicPlace: HistoricPlace) {
        historicPlacesRepository.addHistoricPlace(historicPlace)
    }

    fun updateHistoricPlace(historicPlace: HistoricPlace) {
        historicPlacesRepository.updatePlaceDetails(historicPlace)
    }

    fun deleteHistoricPlaceDetails(historicPlace: HistoricPlace) {
        historicPlacesRepository.deletePlace(historicPlace)
    }

    fun findPlaceById(placeId: Long) {
        viewModelScope.launch {
            // Call the corresponding method in the repository to fetch the place by ID
            historicPlacesRepository.findPlaceById(placeId)
        }
    }

    val historicPlaceList: LiveData<List<HistoricPlace>> = historicPlacesRepository.allPlaces
    val foundPlace: LiveData<HistoricPlace?> = historicPlacesRepository.foundPlace

    companion object {
        private const val TAG = "HistoricalPlacesListVM"
    }
}
