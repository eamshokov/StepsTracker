package ru.eamshokov.stepstracker.ui.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.eamshokov.domain.uiinteractor.RegisterUsecase
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUsecase:RegisterUsecase
): ViewModel() {
    val username = MutableStateFlow("")
    val password = MutableStateFlow("")

    private val _registerResult = MutableSharedFlow<Boolean>()
    val registerResult:SharedFlow<Boolean>
        get() = _registerResult.asSharedFlow()

    private val _error = MutableStateFlow<ErrorState>(ErrorState.NoErrors)
    val error:StateFlow<ErrorState>
        get() = _error.asStateFlow()

    fun register(){
        viewModelScope.launch {
            if(validate(username.value, password.value)){
                registerUsecase.register(username.value, password.value)
                    .catch {
                        _registerResult.emit(false)
                        _error.value = ErrorState.UsernameOrPasswordAreEmpty
                    }
                    .collect { result ->
                        _registerResult.emit(result)
                        _error.value = ErrorState.UserExistsError
                    }
            }
            else{
                _registerResult.emit(false)
            }
        }
    }

    private fun validate(login:String, password:String):Boolean {
        if(login.length < UNAME_MIN_LENGTH){
            _error.value = ErrorState.UsernameIsTooShortError
            return false
        }
        if(password.length < PASSWD_MIN_LENGTH){
            _error.value = ErrorState.PasswordIsTooShortError
            return false
        }
        return true
    }

    sealed class ErrorState(val message:String) {
        object UsernameIsTooShortError: ErrorState("Name must be at least 3 symbols")
        object PasswordIsTooShortError: ErrorState("Password must be at least 8 symbols")
        object UserExistsError: ErrorState("User with such name exists")
        object UsernameOrPasswordAreEmpty: ErrorState("Username or password are empty")
        object NoErrors: ErrorState("")
    }

    companion object {
        const val UNAME_MIN_LENGTH = 3
        const val PASSWD_MIN_LENGTH = 3

    }
}