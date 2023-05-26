package com.ismael.petsinnocv.domain

import com.ismael.petsinnocv.data.UsersRepository
import com.ismael.petsinnocv.data.model.UserRequest
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val repository : UsersRepository){
  suspend operator fun invoke(user: UserRequest): Int {
    return repository.signUpUser(user)
  }
}