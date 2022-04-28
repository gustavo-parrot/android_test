package io.parrotsoftware.qa_network.domain.responses

enum class ApiProductAvailability(value: String) {
    AVAILABLE("AVAILABLE"),
    UNAVAILABLE("UNAVAILABLE")
}

data class ApiCategory(
    val uuid: String,
    val name: String,
    val sortPosition: Int
)

data class ApiProduct(
    val uuid: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val price: Float,
    val availability: ApiProductAvailability,
    val category: ApiCategory
)
