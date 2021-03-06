package ru.eamshokov.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import ru.eamshokov.data.database.entity.UserEntity

@Dao
interface UsersDao {

    @Query("SELECT * FROM users WHERE username = :login AND passwordHash = :passwordHash")
    suspend fun getUser(login:String, passwordHash:String): UserEntity?

    @Insert(onConflict = IGNORE)
    suspend fun insertUser(user:UserEntity)
}