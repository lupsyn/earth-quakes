package com.challange.hilt.di

import android.content.Context
import androidx.room.Room
import com.challange.hilt.BuildConfig
import com.challange.hilt.data.MainRepository
import com.challange.hilt.data.MainRepositoryImpl
import com.challange.hilt.data.db.AppDatabase
import com.challange.hilt.data.db.dao.EarthQuakeDao
import com.challange.hilt.data.network.Api
import com.challange.hilt.data.network.ApiImpl
import com.challange.hilt.data.network.ApiService
import com.challange.hilt.ui.main.interactors.MainInteractor
import com.challange.hilt.ui.main.interactors.MainInteractorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}

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
}

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

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {
    @Provides
    fun provideUserName() = BuildConfig.USER_NAME

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiImpl(apiImpl: ApiImpl): Api = apiImpl
}