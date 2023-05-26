package com.ismael.petsinnocv.data

import com.ismael.petsinnocv.data.database.dao.UserDao
import com.ismael.petsinnocv.data.model.User
import com.ismael.petsinnocv.data.model.UserRequest
import com.ismael.petsinnocv.data.network.UserService
import javax.inject.Inject

class UsersRepository @Inject constructor(
  private val api : UserService,
  private val userDao : UserDao
){

  suspend fun getUsers() : List<User> {
    return api.getUsers()
  }

  suspend fun getUserById(id : Int) : User? {
    return api.getUsersById(id)
  }

  suspend fun clearUsers() {
    userDao.deleteAllUsers()
  }

  suspend fun insertUsers(users : List<User>) {
    userDao.insertAll(users)
  }

  suspend fun getUsersFromDatabase() : List<User>{
    return userDao.getAllUsers()
  }

  suspend fun deleteUser(id: Int) : Int {
    return api.deleteUser(id)
  }

  suspend fun signUpUser(user: UserRequest): Int {
    return api.signUpUser(user)
  }


}