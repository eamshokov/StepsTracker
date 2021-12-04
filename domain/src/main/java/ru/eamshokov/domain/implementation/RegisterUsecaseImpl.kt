package ru.eamshokov.domain.implementation

import kotlinx.coroutines.flow.flow
import ru.eamshokov.domain.datainteractor.UserStorage
import ru.eamshokov.domain.exceptions.IncorrectAuthDataException
import ru.eamshokov.domain.uiinteractor.RegisterUsecase

class RegisterUsecaseImpl(
    private val userStorage: UserStorage
):RegisterUsecase {

    override fun register(login: String, password: String) = flow {
        if(login.isEmpty() || password.isEmpty()){
            throw IncorrectAuthDataException()
        }
        val userExists = userStorage.getUser(login, password) != null
        emit(
            if(userExists) false
            else {
                userStorage.saveUser(login, password)
                true
            }
        )
    }
}