package ru.eamshokov.domain.datainteractor

import ru.eamshokov.domain.entity.User

interface UserStorage {
    suspend fun getUser(login:String, password:String): User?

    suspend fun saveUser(login: String, password: String)
}