package com.ismael.petsinnocv.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ismael.petsinnocv.core.Constants
import com.ismael.petsinnocv.data.model.User
import com.ismael.petsinnocv.domain.DeleteUserUseCase
import com.ismael.petsinnocv.domain.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
  private val getUsersUseCase: GetUsersUseCase,
  private val deleteUserUseCase: DeleteUserUseCase
) : ViewModel() {
  private val _users = MutableLiveData<List<User>>()
  val users: LiveData<List<User>>
    get() = _users

  private val _state = MutableStateFlow<UsersViewModelState>(UsersViewModelState.Initial)
  val state: StateFlow<UsersViewModelState> = _state


  init {
    setState(UsersViewModelState.Initial)
    getUsers()
  }

  fun setState(state: UsersViewModelState) {
    _state.update { state }
  }

  /**
   * Get all users from the server
   * @author : Ismael Toro
   */
  private fun getUsers() = viewModelScope.launch {
    setState(UsersViewModelState.Loading)
    val result = getUsersUseCase()
    if (!result.isNullOrEmpty()) {
      _users.postValue(result)
      setState(UsersViewModelState.Success)
    } else {
      setState(UsersViewModelState.Error("No se han encontrado usuarios"))
    }
  }

  /**
   * Delete user from the list
   * When a user is deleted whe have to get all the users another time
   */
  fun deleteUser(user: User) = viewModelScope.launch{
    setState(UsersViewModelState.Loading)
    val result = deleteUserUseCase(user.id)
    if (result == Constants.SUCCES) {
      getUsers()
      setState(UsersViewModelState.Success)
    } else {
      setState(UsersViewModelState.Error("No ha sido posible eliminar el usuario : $user"))
    }


  }

}

// STATES OF THE UI
sealed class UsersViewModelState {
  object Initial : UsersViewModelState()
  object Loading : UsersViewModelState()
  object Success : UsersViewModelState()
  data class Error(val errorMessage: String) : UsersViewModelState()
}