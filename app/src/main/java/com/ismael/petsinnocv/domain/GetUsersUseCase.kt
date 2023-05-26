package com.ismael.petsinnocv.domain

import com.ismael.petsinnocv.data.UsersRepository
import com.ismael.petsinnocv.data.model.User
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val repository : UsersRepository){
  suspend operator fun invoke() : List<User> {
    val users = repository.getUsers()

    return if (users.isNotEmpty()) {
      repository.clearUsers()
      repository.insertUsers(users)
      users
    } else {
      repository.getUsersFromDatabase()
    }
  }

}