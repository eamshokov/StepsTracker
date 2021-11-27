package ru.eamshokov.stepstracker.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.eamshokov.domain.datainteractor.UserStorage
import ru.eamshokov.domain.uiinteractor.LoginUsecase
import ru.eamshokov.domain.implementation.LoginUsecaseImpl

@Module
@InstallIn(ViewModelComponent::class)
class UsecasesModule {

    @Provides
    fun provideLoginUsecase(userStorage: UserStorage): LoginUsecase = LoginUsecaseImpl(userStorage)
}