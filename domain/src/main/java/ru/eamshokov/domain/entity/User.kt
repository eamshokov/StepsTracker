package ru.eamshokov.domain.entity

data class User(
    val id:Long,
    val login:String,
    val passwordHash:String
)
