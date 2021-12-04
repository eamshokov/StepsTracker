package ru.eamshokov.data

import ru.eamshokov.data.database.dao.UsersDao
import ru.eamshokov.data.database.entity.UserEntity
import ru.eamshokov.data.mappers.toUser
import ru.eamshokov.domain.datainteractor.UserStorage
import ru.eamshokov.domain.entity.User

class UserStorageImpl(
    private val usersDao: UsersDao
) : UserStorage {

    override suspend fun getUser(login: String, password: String): User? {
        return usersDao.getUser(login, password)?.toUser()
    }

    override suspend fun saveUser(login: String, password: String) {
        usersDao.insertUser(UserEntity(login, password, 0))
    }
}