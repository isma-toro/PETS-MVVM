package com.ismael.petsinnocv.data.network

import retrofit2.http.GET
import java.util.Date

interface HealthApiClient {

  // I don´t understand this call
  @GET("/Health")
  fun getHealth() : Date

}