package com.lnavamuelp.historicalroots.di

import android.content.Context
import androidx.room.Room
import com.lnavamuelp.historicalroots.database.HistoricPlaceDao
import com.lnavamuelp.historicalroots.database.HistoricPlacesRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
private object DatabaseModule {

    @Provides
    fun provideHistoricPlaceDao(appDatabase: HistoricPlacesRoomDatabase): HistoricPlaceDao {
        return appDatabase.historicPlaceDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): HistoricPlacesRoomDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            HistoricPlacesRoomDatabase::class.java,
            "appDB"
        ).build()
    }

}