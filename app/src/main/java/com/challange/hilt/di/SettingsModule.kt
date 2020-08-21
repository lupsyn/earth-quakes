package com.challange.hilt.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Named

@Module
@InstallIn(ApplicationComponent::class)
object SettingsModule {

    @Provides
    @Named("API_URL")
    fun provideBaseUrl() = "http://api.geonames.org/"

}