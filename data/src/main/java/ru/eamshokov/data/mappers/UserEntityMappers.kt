package ru.eamshokov.data.mappers

import ru.eamshokov.data.database.entity.UserEntity
import ru.eamshokov.domain.entity.User

fun UserEntity.toUser() = User(
    id,
    username,
    passwordHash
)