package com.challange.hilt.di

import android.content.Context
import androidx.room.Room
import com.challange.hilt.data.db.AppDatabase
import com.challange.hilt.data.db.dao.EarthQuakeDao
import com.challange.hilt.data.db.dao.EarthQuakeResponseConfigDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "earthQuake.db"
        ).build()

    @Provides
    fun provideEarthQuakeDao(database: AppDatabase): EarthQuakeDao = database.earthQuakeDao()

    @Provides
    fun provideEarthQuakeResponseConfigDao(database: AppDatabase): EarthQuakeResponseConfigDao =
        database.earthQuakeResponseConfigDao()
}