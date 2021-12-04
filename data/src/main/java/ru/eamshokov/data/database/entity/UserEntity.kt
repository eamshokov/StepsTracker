package ru.eamshokov.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity (
    val username:String,
    val passwordHash:String,
    @PrimaryKey(autoGenerate = true)
    var id: Long,
)