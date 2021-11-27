package ru.eamshokov.data

import ru.eamshokov.domain.datainteractor.UserStorage
import ru.eamshokov.domain.entity.User

class UserStorageImpl : UserStorage {
    override suspend fun getUser(login: String, password: String): User? {
        return if(login == "login" && password=="password") User(0, login, password)
        else null
    }
}