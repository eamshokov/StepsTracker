package ru.eamshokov.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.eamshokov.data.database.dao.UsersDao
import ru.eamshokov.data.database.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class DatabaseHandler:RoomDatabase() {
    abstract fun usersDao(): UsersDao

    companion object {
        fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, DatabaseHandler::class.java, "MyDatabase")
                .fallbackToDestructiveMigration()
                .build()
    }
}