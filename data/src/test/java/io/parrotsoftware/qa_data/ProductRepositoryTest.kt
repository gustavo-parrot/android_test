package io.parrotsoftware.qa_data

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.parrotsoftware.qa_data.datasources.ProductDataSource
import io.parrotsoftware.qa_data.domain.Category
import io.parrotsoftware.qa_data.domain.Product
import io.parrotsoftware.qa_data.domain.RepositoryResult
import io.parrotsoftware.qa_data.repositories.ProductRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ProductRepositoryTest {
    @MockK
    private lateinit var productDataSource: ProductDataSource

    private lateinit var productRepository: ProductRepository

    @Before
    fun setup(){
        MockKAnnotations.init(this, relaxed = true)
        productRepository = ProductRepository(productDataSource)
    }

    @Test
    fun `test get products`() = runBlocking{
        val dummyProducts = listOf(
            Product(
                id = "1",
                name = "name",
                description = "description",
                image = "image",
                price = 0f,
                isAvailable = true,
                category = Category(
                    id = "1",
                    name = "name",
                    position = 0
                )
            ),
            Product(
                id = "2",
                name = "name",
                description = "description",
                image = "image",
                price = 0f,
                isAvailable = true,
                category = Category(
                    id = "2",
                    name = "name",
                    position = 1
                )
            )
        )
        val response = RepositoryResult(dummyProducts)
        coEvery {
            productDataSource.getProducts(any(),any())
        }answers {
            response
        }

        val result = productRepository.getProducts("acessToken","storeID")
        assertEquals(response, result)

    }

    @Test
    fun `test set product state`()= runBlocking{
        val response = RepositoryResult(null)
        coEvery {
            productDataSource.setProductState(any(),any(),any())
        }answers {
            response
        }

       val result =
           productRepository
               .setProductState(
                   "accessToken",
                   "1",
                   true
               )
        assertEquals(response,result)
    }


}