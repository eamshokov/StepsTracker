package ru.eamshokov.domain.uiinteractor

import kotlinx.coroutines.flow.Flow

interface RegisterUsecase {

    fun register(login:String, password:String): Flow<Boolean>
}