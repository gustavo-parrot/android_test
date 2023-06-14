package io.parrotsoftware.qa_data

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.parrotsoftware.qa_data.datasources.UserLocalDataSource
import io.parrotsoftware.qa_data.datasources.UserRemoteDataSource
import io.parrotsoftware.qa_data.domain.RepositoryResult
import io.parrotsoftware.qa_data.repositories.UserRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UserRepositoryTest {


    private lateinit var userRepository: UserRepository

    @MockK
    private lateinit var userLocalDataSource : UserLocalDataSource

    @MockK
    private lateinit var userRemoteDataSource: UserRemoteDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        userRepository = UserRepository(userRemoteDataSource,userLocalDataSource)

    }


    @Test
    fun `test user exist`() = runBlocking{
        val response  =  RepositoryResult(true)
        coEvery {
            userLocalDataSource.userExists()
        }answers {
           response
        }

        val result =  userRepository.userExists()
        assertEquals(response,result)

    }

    @Test
    fun `test login`() = runBlocking{
        val response = RepositoryResult(null)
        coEvery {
            userRemoteDataSource.login(any(),any())
        }answers {
            response
        }
        val result = userRepository.login("email","password")
        assertEquals(response,result)
    }

}