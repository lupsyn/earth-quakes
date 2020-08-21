package com.challange.hilt.di

import com.challange.hilt.data.MainRepository
import com.challange.hilt.data.MainRepositoryImpl
import com.challange.hilt.ui.main.interactors.MainInteractor
import com.challange.hilt.ui.main.interactors.MainInteractorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideMainRepository(mainRepositoryImpl: MainRepositoryImpl): MainRepository =
        mainRepositoryImpl

    @Singleton
    @Provides
    fun provideMainInteractor(mainInteractorImpl: MainInteractorImpl): MainInteractor =
        mainInteractorImpl
}