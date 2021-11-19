package ru.eamshokov.stepstracker.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(): ViewModel() {

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
            _loginResult.emit(true)
        }
    }

    sealed class ErrorState(
        val message: String
    ){
        class UsernameError(message:String): ErrorState(message)
        class PasswordError(message:String): ErrorState(message)
        class LoginError(message:String): ErrorState(message)
        object NoErrors:ErrorState("")
    }
}