package ru.eamshokov.stepstracker

import app.cash.turbine.test
import org.junit.Assert.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.internal.verification.VerificationModeFactory.times
import ru.eamshokov.domain.uiinteractor.LoginUsecase
import ru.eamshokov.stepstracker.ui.screen.login.LoginViewModel

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class LoginViewModelTest {

    lateinit var loginUsecase: LoginUsecase
    lateinit var loginViewModel: LoginViewModel

    private val loginCorrect = "login"
    private val loginIncorrect = "login123"

    private val passwordCorrect = "password"
    private val passwordIncorrect = "password1234"

    @Before
    fun setup(){
        loginUsecase = Mockito.mock(LoginUsecase::class.java)
        Mockito.`when`(loginUsecase.login(loginCorrect, passwordCorrect)).thenReturn(flow{emit(true)})
        Mockito.`when`(loginUsecase.login(loginIncorrect, passwordIncorrect)).thenReturn(flow{emit(false)})
        Mockito.`when`(loginUsecase.login(loginCorrect, passwordIncorrect)).thenReturn(flow{emit(false)})
        Mockito.`when`(loginUsecase.login(loginIncorrect, passwordCorrect)).thenReturn(flow{emit(false)})
        loginViewModel = LoginViewModel(loginUsecase)
        Dispatchers.setMain(TestCoroutineDispatcher())
    }

    @Test
    fun test_loginReturnsTrueWithCorrectLoginAndPassword() = runBlockingTest{
        loginViewModel.username.value = loginCorrect
        loginViewModel.password.value = passwordCorrect
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
        loginViewModel.username.value = loginIncorrect
        loginViewModel.password.value = passwordIncorrect
        val job = launch(start= CoroutineStart.LAZY){
            loginViewModel.login()
        }
        loginViewModel.loginResult.test {
            job.start()
            assertEquals(false, awaitItem())
        }
    }

    @Test
    fun test_loginEmitsErrorStateIfLoginOrPasswordAreIncorrect() = runBlockingTest {
        loginViewModel.username.value = loginCorrect
        loginViewModel.password.value = passwordIncorrect
        loginViewModel.login()
        loginViewModel.error.test {
            assertEquals(true,awaitItem() is LoginViewModel.ErrorState.LoginError)
        }
    }

    @Test
    fun test_loginEmitsUsernameIsEmptyErrorStateIfLoginIsempty() = runBlockingTest {
        loginViewModel.username.value = ""
        loginViewModel.password.value = ""
        loginViewModel.login()
        loginViewModel.error.test {
            assertEquals(true, awaitItem() is LoginViewModel.ErrorState.UsernameIsEmptyError)
        }
    }

    @Test
    fun test_loginEmitsPasswordIsEmptyErrorStateIfPasswordIsEmpty() = runBlockingTest {
        loginViewModel.username.value = loginCorrect
        loginViewModel.password.value = ""
        loginViewModel.login()
        loginViewModel.error.test {
            assertEquals(true, awaitItem() is LoginViewModel.ErrorState.PasswordIsEmptyError)
        }
    }

    @Test
    fun test_loginCallsLoginMethodFromUsecaseIfAuthDataAreNotEmpty() = runBlockingTest {
        loginViewModel.username.value = loginCorrect
        loginViewModel.password.value = passwordCorrect
        launch{
            loginViewModel.login()
            Mockito.verify(loginUsecase, times(1)).login(loginCorrect, passwordCorrect)
        }
    }

    @Test
    fun test_loginDoesntCallLoginMethodFromUsecaseIfLoginEmpty() = runBlockingTest {
        loginViewModel.username.value = ""
        loginViewModel.password.value = passwordCorrect
        launch{
            loginViewModel.login()
            Mockito.verify(loginUsecase, times(0)).login("", passwordCorrect)
        }
    }

    @Test
    fun test_loginDoesntCallLoginMethodFromUsecaseIfPasswordEmpty() = runBlockingTest {
        loginViewModel.username.value = loginCorrect
        loginViewModel.password.value = ""
        launch{
            loginViewModel.login()
            Mockito.verify(loginUsecase, times(0)).login(loginCorrect, "")
        }
    }
}