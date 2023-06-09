package io.parrotsoftware.qatest.data.domain

data class ProductNetworkEntity(
    val id: String,
    val name: String,
    val description: String,
    val image: String,
    val price: Float,
    val isAvailable: Boolean,
    val category: CategoryNetworkEntity
)