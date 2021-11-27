package ru.eamshokov.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.internal.verification.VerificationModeFactory.times
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import ru.eamshokov.domain.datainteractor.UserStorage
import ru.eamshokov.domain.entity.User
import ru.eamshokov.domain.exceptions.IncorrectAuthDataException
import ru.eamshokov.domain.implementation.LoginUsecaseImpl
import ru.eamshokov.domain.uiinteractor.LoginUsecase

@RunWith(JUnit4::class)
class LoginUsecaseTest {

    private val loginCorrect = "login"
    private val loginIncorrect = "login123"

    private val passwordCorrect = "password"
    private val passwordIncorrect = "password1234"

    lateinit var userStorage: UserStorage
    lateinit var loginUsecase: LoginUsecase

    @Before
    fun setup(){
        //userStorage = Mockito.mock(UserStorage::class.java)
        userStorage = mock {
            on { runBlocking {  getUser(loginCorrect, passwordCorrect) } }.doReturn(User(0, loginCorrect, passwordCorrect))
            on { runBlocking { getUser(loginIncorrect, passwordIncorrect) }}.doReturn(null)
        }

        loginUsecase = LoginUsecaseImpl(userStorage)
        Dispatchers.setMain(TestCoroutineDispatcher())
    }

    @Test
    fun test_loginShouldReturnFlowWithTrueIfAuthDataAreCorrect() = runBlockingTest{
        assertEquals(true, loginUsecase.login(loginCorrect, passwordCorrect).first())
    }

    @Test
    fun test_loginShouldRetirnFlowWithFalseIfAuthDataAreNotCorrect() = runBlockingTest {
        assertEquals(false, loginUsecase.login(loginIncorrect, passwordIncorrect).first())
    }

    @Test
    fun test_loginShouldThrowIncorrectDataExceptionIfLoginIsEmptyOrBlank() = runBlockingTest {
        try {
            loginUsecase.login("", passwordCorrect).first()
            assertTrue(false)
        } catch (e:IncorrectAuthDataException){
            assertTrue(true)
        }
    }

    @Test
    fun test_loginShouldThrowIncorrectDataExceptionIfPasswordIsEmptyOrBlank() = runBlockingTest {
        try {
            loginUsecase.login(loginCorrect, "").first()
            assertTrue(false)
        } catch (e:IncorrectAuthDataException){
            assertTrue(true)
        }
    }

    @Test
    fun test_loginShouldCallGetUserMethodFromUserStorage() = runBlockingTest {
        loginUsecase.login(loginCorrect, passwordCorrect).first()
        Mockito.verify(userStorage, times(1)).getUser(loginCorrect, passwordCorrect)
    }

}