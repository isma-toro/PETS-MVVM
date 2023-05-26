package com.ismael.petsinnocv.di

import com.ismael.petsinnocv.core.Constants
import com.ismael.petsinnocv.data.network.HealthApiClient
import com.ismael.petsinnocv.data.network.UserApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
  @Singleton
  @Provides
  fun provideRetrofit() : Retrofit {
    return Retrofit.Builder()
      .baseUrl(Constants.URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }


  @Singleton
  @Provides
  fun provideUserClient(retrofit: Retrofit) : UserApiClient {
    return retrofit.create(UserApiClient::class.java)
  }

  @Singleton
  @Provides
  fun providesHealthClient(retrofit: Retrofit) : HealthApiClient {
    return retrofit.create(HealthApiClient::class.java)
  }


}