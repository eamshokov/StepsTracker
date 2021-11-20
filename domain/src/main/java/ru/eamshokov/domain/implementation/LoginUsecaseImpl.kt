package ru.eamshokov.domain.implementation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.eamshokov.domain.LoginUsecase

class LoginUsecaseImpl:LoginUsecase {
    override fun login(login: String, password: String): Flow<Boolean> = flow {
        emit(login == "login" && password == "password")
    }
}