package ru.eamshokov.stepstracker.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.eamshokov.data.UserStorageImpl
import ru.eamshokov.data.database.dao.UsersDao
import ru.eamshokov.domain.datainteractor.UserStorage

@Module
@InstallIn(ViewModelComponent::class)
class StoragesModule {

    @Provides
    fun provideUserStorage(usersDao: UsersDao):UserStorage = UserStorageImpl(usersDao)

}