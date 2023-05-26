package com.ismael.petsinnocv.data.network

import com.ismael.petsinnocv.data.model.User
import com.ismael.petsinnocv.data.model.UserRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApiClient {
  @GET("/api/User")
  suspend fun getUsers() : Response<List<User>>

  @POST("api/User")
  suspend fun signUpUser(
    @Body user: UserRequest
  ) : Response<ResponseBody>

  @DELETE("/api/User/{id}")
  suspend fun deleteUser(
    @Path("id") id : Int
  ) : Response<ResponseBody>

  @GET("api/User/{id}")
  suspend fun getUserById(
    @Path("id") id : Int
  ) : Response<User>





}