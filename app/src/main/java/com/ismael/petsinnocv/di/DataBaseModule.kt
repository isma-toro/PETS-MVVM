package com.ismael.petsinnocv.di

import android.app.Application
import androidx.room.Room
import com.ismael.petsinnocv.data.database.PetsInnocvDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
  @Provides
  @Singleton
  fun provideRoom(application: Application) : PetsInnocvDatabase{
    return Room.databaseBuilder(
      application.applicationContext,
      PetsInnocvDatabase::class.java,
      "pets_innocv_database").build()
  }

  @Singleton
  @Provides
  fun provideUserDao(petsInnocvDatabase: PetsInnocvDatabase) = petsInnocvDatabase.userDao()

}