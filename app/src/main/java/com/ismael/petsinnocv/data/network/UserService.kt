package com.ismael.petsinnocv.data.network

import com.ismael.petsinnocv.core.Constants
import com.ismael.petsinnocv.data.model.User
import com.ismael.petsinnocv.data.model.UserRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserService @Inject constructor(private val api : UserApiClient) {
  suspend fun getUsers() : List<User> {
    return withContext(Dispatchers.IO) {
      val response = api.getUsers()
      response.body() ?: emptyList()
    }
  }

  suspend fun getUsersById(id: Int) : User? {
    return withContext(Dispatchers.IO) {
      val response = api.getUserById(id)
      response.body()
    }
  }

  suspend fun deleteUser(id: Int) : Int{
    return withContext(Dispatchers.IO) {
      val response = api.deleteUser(id)
      if (response.isSuccessful) {
        Constants.SUCCES
      } else {
        Constants.ERROR
      }
    }
  }

  suspend fun signUpUser(user: UserRequest): Int {
    return withContext(Dispatchers.IO) {
      val response = api.signUpUser(user)
      if (response.isSuccessful) {
        Constants.SUCCES
      } else {
        Constants.ERROR
      }
    }

  }


}