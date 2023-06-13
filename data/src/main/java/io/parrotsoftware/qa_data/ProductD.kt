package io.parrotsoftware.qa_data

data class ProductD(
    val id: String,
    val name: String,
    val description: String,
    val image: String,
    val price: Float,
    val isAvailable: Boolean,
    val category: CategoryD,
)