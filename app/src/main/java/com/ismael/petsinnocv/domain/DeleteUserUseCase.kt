package com.ismael.petsinnocv.domain

import com.ismael.petsinnocv.data.UsersRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(private val repository: UsersRepository) {
    suspend operator fun invoke(id : Int) : Int{
      return repository.deleteUser(id)
    }
}