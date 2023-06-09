package io.parrotsoftware.qatest.data.mapper

import io.parrotsoftware.qatest.data.domain.local.Category
import io.parrotsoftware.qatest.data.domain.local.Product
import io.parrotsoftware.qatest.data.domain.CategoryNetworkEntity
import io.parrotsoftware.qatest.data.domain.ProductNetworkEntity

object EntityMapper {
    fun mapFromProductEntity(entity: ProductNetworkEntity): Product {
        return Product(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            image = entity.image,
            price = entity.price,
            isAvailable = entity.isAvailable,
            category = mapFromCategoryEntity( entity.category)
        )
    }


    fun mapFromCategoryEntity(entity: CategoryNetworkEntity): Category {
        return Category(
            id = entity.id,
            name = entity.name,
            position = entity.position
        )
    }



}