package com.lnavamuelp.historicalroots.di

import com.lnavamuelp.historicalroots.database.HistoricPlaceDao
import com.lnavamuelp.historicalroots.repository.HistoricPlacesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideEmployeeRepository(HistoricPlaceDao: HistoricPlaceDao): HistoricPlacesRepository {
        return HistoricPlacesRepository(HistoricPlaceDao)
    }

}