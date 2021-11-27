package ru.eamshokov.domain.uiinteractor

import kotlinx.coroutines.flow.Flow

interface LoginUsecase {
    fun login(login:String, password:String): Flow<Boolean>
}