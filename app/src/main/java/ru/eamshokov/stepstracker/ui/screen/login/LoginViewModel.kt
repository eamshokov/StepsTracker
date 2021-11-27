package ru.eamshokov.stepstracker.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.eamshokov.domain.uiinteractor.LoginUsecase
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUsecase: LoginUsecase
): ViewModel() {
      val username = MutableStateFlow("")
      val password = MutableStateFlow("")

      private val _error = MutableStateFlow<ErrorState>(ErrorState.NoErrors)
      val error:StateFlow<ErrorState>
          get() = _error.asStateFlow()

      private val _loginResult = MutableSharedFlow<Boolean>()
      val loginResult: SharedFlow<Boolean>
        get() = _loginResult.asSharedFlow()

      fun login(){
        viewModelScope.launch {
            if(validate(username.value, password.value)){
                loginUsecase.login(username.value, password.value)
                    .collect {
                        _loginResult.emit(it)
                        if(!it){
                            _error.value = ErrorState.LoginError
                        }
                    }
            }
        }
      }

    private fun validate(login:String, password:String):Boolean{
        if(login.isEmpty()){
            _error.value = ErrorState.UsernameIsEmptyError
            return false
        }
        if(password.isEmpty()){
            _error.value = ErrorState.PasswordIsEmptyError
            return false
        }
        return true
    }

      sealed class ErrorState(
        val message: String
      ){
        object UsernameIsEmptyError: ErrorState("Name must not be empty")
        object PasswordIsEmptyError: ErrorState("Password must not be empty")
        object LoginError: ErrorState("Name or password are incorrect")
        object NoErrors:ErrorState("")
      }
}