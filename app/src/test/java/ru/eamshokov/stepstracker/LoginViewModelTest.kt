package ru.eamshokov.stepstracker

import app.cash.turbine.test
import org.junit.Assert.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import ru.eamshokov.domain.LoginUsecase
import ru.eamshokov.domain.implementation.LoginUsecaseImpl
import ru.eamshokov.stepstracker.ui.screen.login.LoginViewModel

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class LoginViewModelTest {

    lateinit var loginUsecase: LoginUsecase
    lateinit var loginViewModel: LoginViewModel

    @Before
    fun setup(){
        loginUsecase = LoginUsecaseImpl()
        loginViewModel = LoginViewModel(loginUsecase)
        Dispatchers.setMain(TestCoroutineDispatcher())
    }

    @Test
    fun test_loginReturnsTrueWithCorrectLoginAndPassword() = runBlockingTest{
        val login = "login"
        val password = "password"
        loginViewModel.username.value = login
        loginViewModel.password.value = password
        val job = launch(start= CoroutineStart.LAZY){
            loginViewModel.login()
        }
        loginViewModel.loginResult.test {
            job.start()
            assertEquals(true, awaitItem())
        }
    }

    @Test
    fun test_loginReturnsFalseIfLoginAndPasswordAreIncorrect() = runBlocking{
        val login = "login"
        val password = "password123123"
        loginViewModel.username.value = login
        loginViewModel.password.value = password
        val job = launch(start= CoroutineStart.LAZY){
            loginViewModel.login()
        }
        loginViewModel.loginResult.test {
            job.start()
            assertEquals(false, awaitItem())
        }
    }

    @Test
    fun test_loginEmitsErrorSatateifLoginOrPasswordAreIncorrect() = runBlockingTest {
        val login = "login"
        val password = "password123123"
        loginViewModel.username.value = login
        loginViewModel.password.value = password
        loginViewModel.login()
        loginViewModel.error.test {
            assertEquals(true,awaitItem() is LoginViewModel.ErrorState.LoginError)
        }
    }

    @Test
    fun test_loginEmitsUsernameIsEmptyErrorStateIfLoginIsempty() = runBlockingTest {
        val login = ""
        val password =""
        loginViewModel.username.value = login
        loginViewModel.password.value = password
        loginViewModel.login()
        loginViewModel.error.test {
            assertEquals(true, awaitItem() is LoginViewModel.ErrorState.UsernameIsEmptyError)
        }
    }

    @Test
    fun test_loginEmitsPasswordIsEmptyErrorStateIfPasswordIsEmpty() = runBlockingTest {
        val login = "login"
        val password =""
        loginViewModel.username.value = login
        loginViewModel.password.value = password
        loginViewModel.login()
        loginViewModel.error.test {
            assertEquals(true, awaitItem() is LoginViewModel.ErrorState.PasswordIsEmptyError)
        }
    }
}