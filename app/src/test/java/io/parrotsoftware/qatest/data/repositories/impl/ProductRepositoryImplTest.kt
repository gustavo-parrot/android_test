package io.parrotsoftware.qatest.data.repositories.impl

import io.mockk.mockk
import io.parrotsoftware.qa_network.domain.NetworkResult
import io.parrotsoftware.qa_network.domain.responses.ApiListResponse
import io.parrotsoftware.qa_network.domain.responses.ApiProduct
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ProductRepositoryImplTest {
    private lateinit var testSubject: ProductRepositoryImpl

    @Before
    fun setup() {
        testSubject = mockk()
    }

    @Test
    fun `get products successfully`() = runBlocking {
        val mockResponse: NetworkResult<ApiListResponse<ApiProduct>>
    }
}