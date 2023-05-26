package com.ismael.petsinnocv.presentation.viewModel

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ismael.petsinnocv.core.Constants
import com.ismael.petsinnocv.data.model.User
import com.ismael.petsinnocv.data.model.UserRequest
import com.ismael.petsinnocv.domain.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
  private val signUpUseCase: SignUpUseCase
) : ViewModel() {
  private val _user = MutableLiveData<User>()
  val user: LiveData<User>
    get() = _user

  private val _state = MutableStateFlow<SignUpViewModelState>(SignUpViewModelState.Initial)
  val state: StateFlow<SignUpViewModelState> = _state


  fun setState(state: SignUpViewModelState) {
    _state.update { state }
  }

  fun checkUser(user: Editable?, birthdate: Editable?) {
    setState(SignUpViewModelState.Loading)
   if (user.isNullOrEmpty() || birthdate.isNullOrEmpty()) {
     setState(SignUpViewModelState.dataError)
   } else {
     signUpUser(UserRequest(
       name = user.toString(),
       birthdate = birthdate.toString()
     ))
   }
  }

  private fun signUpUser(user: UserRequest) = viewModelScope.launch{
    val result = signUpUseCase(user)
    if (result == Constants.SUCCES) {
      setState(SignUpViewModelState.Success)
    } else {
      setState(SignUpViewModelState.Error("Registro fallido"))
    }
  }

}

sealed class SignUpViewModelState {
  object Initial : SignUpViewModelState()
  object Loading : SignUpViewModelState()
  object Success : SignUpViewModelState()
  data class Error(val errorMessage: String) : SignUpViewModelState()
  object dataError : SignUpViewModelState()
}