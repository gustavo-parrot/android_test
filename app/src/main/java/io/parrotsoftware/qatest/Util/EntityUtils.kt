package io.parrotsoftware.qatest.Util

import io.parrotsoftware.qatest.data.database.entities.CategoryEntity
import io.parrotsoftware.qatest.data.database.entities.ProductEntity
import io.parrotsoftware.qatest.data.domain.Product
import javax.inject.Singleton

@Singleton
class EntityUtils {

    fun createListProductEntities(products: List<Product>): List<ProductEntity> = products.map {
        with(it) {
            ProductEntity(
                id,
                name,
                description,
                image,
                price,
                isAvailable,
                CategoryEntity(category.id, category.name, category.position)
            )
        }
    }
}
