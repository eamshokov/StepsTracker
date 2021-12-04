package ru.eamshokov.stepstracker

import app.cash.turbine.test
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.internal.verification.VerificationModeFactory.times
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import ru.eamshokov.domain.uiinteractor.RegisterUsecase
import ru.eamshokov.stepstracker.ui.screen.register.RegisterViewModel

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class RegisterViewModelTest {

    private val loginCorrect = "login"
    private val loginIncorrect = "login123"

    private val password = "password"

    lateinit var registerUsecase:RegisterUsecase
    lateinit var registerViewModel:RegisterViewModel

    @Before
    fun setup(){
        registerUsecase = mock {
            on { register(loginCorrect, password)} doReturn flow { emit(true) }
            on { register(loginIncorrect, password)} doReturn flow{ emit(false) }
        }
        registerViewModel = RegisterViewModel(registerUsecase)
        Dispatchers.setMain(TestCoroutineDispatcher())
    }

    @Test
    fun test_registerReturnsTrueIfLoginAndPasswordAreValidAndAvailable() = runBlockingTest {
        registerViewModel.username.value = loginCorrect
        registerViewModel.password.value = password
        val job = launch ( start = CoroutineStart.LAZY ){
            registerViewModel.register()
        }
        registerViewModel.registerResult.test {
            job.start()
            assertEquals(true, awaitItem())
        }
    }

    @Test
    fun test_regiserReturnsFalseIfLoginAndPasswordAreValidAndUnavailable() = runBlockingTest {
        registerViewModel.username.value = ""
        registerViewModel.password.value = ""
        val job = launch ( start = CoroutineStart.LAZY ){
            registerViewModel.register()
        }
        registerViewModel.registerResult.test {
            job.start()
            assertEquals(false, awaitItem())
        }
    }

    @Test
    fun test_registerEmitsErrorIfUsernameIsEmpty() = runBlockingTest {
        registerViewModel.username.value = ""
        registerViewModel.password.value = password
        registerViewModel.register()
        registerViewModel.error.test {
            assertEquals(true, awaitItem() is RegisterViewModel.ErrorState.UsernameIsTooShortError)
        }
    }

    @Test
    fun test_registerEmitsErrorIfPasswordIsEmpty() = runBlockingTest {
        registerViewModel.username.value = loginCorrect
        registerViewModel.password.value = ""
        registerViewModel.register()
        registerViewModel.error.test {
            assertEquals(true, awaitItem() is RegisterViewModel.ErrorState.PasswordIsTooShortError)
        }
    }

    @Test
    fun test_registerEmitsUserExistsIfUserExists() = runBlockingTest {
        registerViewModel.username.value = loginIncorrect
        registerViewModel.password.value = password
        registerViewModel.register()
        registerViewModel.error.test {
            assertEquals(true, awaitItem() is RegisterViewModel.ErrorState.UserExistsError)
        }
    }

    @Test
    fun test_registerCallsRegisterUsecaseIfAuthDataAreValid() = runBlockingTest {
        registerViewModel.username.value = loginCorrect
        registerViewModel.password.value = password
        launch {
            registerViewModel.register()
            Mockito.verify(registerUsecase, times(1))
                .register(loginCorrect, password)
        }
    }
}