package ru.eamshokov.stepstracker.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.eamshokov.data.database.DatabaseHandler
import ru.eamshokov.data.database.dao.UsersDao

@Module
@InstallIn(ViewModelComponent::class)
class DaoModule {

    @Provides
    fun provideDatabaseHandler(@ApplicationContext context: Context): DatabaseHandler =
        DatabaseHandler.buildDatabase(context)

    @Provides
    fun provideUsersDao(dbHandler: DatabaseHandler): UsersDao = dbHandler.usersDao()
}