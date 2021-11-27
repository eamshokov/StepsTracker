package ru.eamshokov.domain.implementation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.eamshokov.domain.datainteractor.UserStorage
import ru.eamshokov.domain.uiinteractor.LoginUsecase
import ru.eamshokov.domain.exceptions.IncorrectAuthDataException

class LoginUsecaseImpl(
    private val userStorage:UserStorage
): LoginUsecase {
    override fun login(login: String, password: String): Flow<Boolean> = flow {
        if(verify(login, password)){
            val user = userStorage.getUser(login, password)
            emit(user != null)
        }else{
            throw IncorrectAuthDataException()
        }
    }

    private fun verify(login:String, password:String):Boolean =
        (login.isNotBlank() && password.isNotBlank())
}