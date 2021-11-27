package ru.eamshokov.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import ru.eamshokov.domain.datainteractor.UserStorage
import ru.eamshokov.domain.entity.User

@RunWith(JUnit4::class)
class UserStorageTest {
    private val loginCorrect = "login"
    private val loginIncorrect = "login123"

    private val passwordCorrect = "password"
    private val passwordIncorrect = "password1234"

    val resultUser = User(0, loginCorrect, passwordCorrect)

    private lateinit var userStorage: UserStorage

    @Before
    fun setup(){
        userStorage = UserStorageImpl()
        Dispatchers.setMain(TestCoroutineDispatcher())
    }

    @Test
    fun test_getUserMustReturnUserEntityWhenDataAreCorrect() = runBlockingTest {
        val user = userStorage.getUser(loginCorrect, passwordCorrect)
        assertEquals(resultUser, user)
    }

    @Test
    fun test_getUserMustReturnNullWhenDataAreIncorect() = runBlockingTest {
        val user = userStorage.getUser(loginIncorrect, passwordIncorrect)
        assertEquals(null, user)
    }

    /*@Test
    fun test_getUserMustRequestDataFrom*/
}