package ru.eamshokov.stepstracker.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import ru.eamshokov.domain.LoginUsecase
import ru.eamshokov.domain.implementation.LoginUsecaseImpl

@Module
@InstallIn(ViewModelComponent::class)
class UsecasesModule {

    @Provides
    fun provideLoginUsecase():LoginUsecase = LoginUsecaseImpl()
}