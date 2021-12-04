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
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import ru.eamshokov.domain.datainteractor.UserStorage
import ru.eamshokov.domain.entity.User
import ru.eamshokov.domain.exceptions.IncorrectAuthDataException
import ru.eamshokov.domain.implementation.RegisterUsecaseImpl
import ru.eamshokov.domain.uiinteractor.RegisterUsecase

@RunWith(JUnit4::class)
class RegisterUsecaseTest {
    private val loginCorrect = "login"
    private val loginIncorrect = "login123"

    private val passwordCorrect = "password"

    lateinit var userStorage:UserStorage
    lateinit var registerUsecase: RegisterUsecase

    @Before
    fun setup(){
        userStorage = mock {
            on { runBlocking {  getUser(loginIncorrect, passwordCorrect) } }.doReturn(User(0, loginCorrect, passwordCorrect))
            on { runBlocking { getUser(loginCorrect, passwordCorrect) }}.doReturn(null)
        }
        registerUsecase = RegisterUsecaseImpl(userStorage)
        Dispatchers.setMain(TestCoroutineDispatcher())
    }

    @Test
    fun test_registerEmitsTrueIfAuthDataValidAndAvailable() = runBlockingTest {
        assertEquals(true, registerUsecase.register(loginCorrect, passwordCorrect).first())
    }

    @Test
    fun test_registerEmitsFalseIfAuthDataNotValidOrUnavailable() = runBlockingTest {
        assertEquals(false, registerUsecase.register(loginIncorrect, passwordCorrect).first())
    }

    @Test
    fun test_registerThrowsExceptionIfLoginIsEmpty() = runBlockingTest {
        try {
            registerUsecase.register("", passwordCorrect).first()
            assertTrue(false)
        }catch (e: IncorrectAuthDataException){
            assertTrue(true)
        }
    }

    @Test
    fun test_registerThrowsExceptionIfPasswordIsEmpty() = runBlockingTest {
        try {
            registerUsecase.register(loginCorrect, "").first()
            assertTrue(false)
        }catch (e: IncorrectAuthDataException){
            assertTrue(true)
        }
    }

    @Test
    fun test_registerCallsGetUserMethodFromUserStorage() = runBlockingTest {
        registerUsecase.register(loginCorrect, passwordCorrect).first()
        Mockito.verify(userStorage, times(1)).getUser(loginCorrect, passwordCorrect)
    }

    @Test
    fun test_registerCallsSaveUserMethodFromUserStorageIfDataValid() = runBlockingTest {
        registerUsecase.register(loginCorrect, passwordCorrect).first()
        Mockito.verify(userStorage, times(1)).saveUser(loginCorrect, passwordCorrect)
    }
}