package io.parrotsoftware.qa_data.mappers

import io.parrotsoftware.qa_data.bd.entities.CategoryEntity
import io.parrotsoftware.qa_data.bd.entities.ProductEntity
import io.parrotsoftware.qa_data.domain.Category
import io.parrotsoftware.qa_data.domain.Product
import javax.inject.Inject

class ProductMapper @Inject constructor() {
 fun toEntity(products:List<Product>) : List<ProductEntity> =
     products.map {product->
         val category = product.category
         ProductEntity(
             id = product.id,
             name = product.name,
             description = product.description,
             image = product.image,
             price = product.price,
             isAvailable = product.isAvailable,
             category = CategoryEntity(
                 id = category.id,
                 name = category.name,
                 position = category.position
             )
         )
     }

 fun toDomain(productsEntity: List<ProductEntity>) : List<Product> =

     productsEntity.map {productEntity ->
         val categoryEntity = productEntity.category
         Product(
             id = productEntity.id,
             name = productEntity.name,
             description = productEntity.description,
             image = productEntity.image,
             price = productEntity.price,
             isAvailable = productEntity.isAvailable,
             category = Category(
                 id = categoryEntity.id,
                 name = categoryEntity.name,
                 position = categoryEntity.position
             )
         )
     }

}