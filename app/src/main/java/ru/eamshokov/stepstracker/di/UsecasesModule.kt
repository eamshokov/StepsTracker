package ru.eamshokov.stepstracker.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.eamshokov.domain.datainteractor.UserStorage
import ru.eamshokov.domain.uiinteractor.LoginUsecase
import ru.eamshokov.domain.implementation.LoginUsecaseImpl
import ru.eamshokov.domain.implementation.RegisterUsecaseImpl
import ru.eamshokov.domain.uiinteractor.RegisterUsecase

@Module
@InstallIn(ViewModelComponent::class)
class UsecasesModule {

    @Provides
    fun provideLoginUsecase(userStorage: UserStorage): LoginUsecase =
        LoginUsecaseImpl(userStorage)

    @Provides
    fun provideRegisterUsecase(userStorage: UserStorage):RegisterUsecase =
        RegisterUsecaseImpl(userStorage)
}