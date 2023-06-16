package io.parrotsoftware.qatest.domain.models

/**
 * Product
 *
 * @author (c) 2023, Parrot Inc.
 */
data class Product(
    val id: String,
    val name: String,
    val description: String,
    val image: String,
    val price: Float,
    val isAvailable: Boolean,
    val category: Category
)
