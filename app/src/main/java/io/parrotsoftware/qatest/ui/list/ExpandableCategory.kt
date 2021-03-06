package io.parrotsoftware.qatest.ui.list

import io.parrotsoftware.qatest.data.domain.Category
import io.parrotsoftware.qatest.data.domain.Product


data class EnabledProduct(
    val product: Product,
    val enabled: Boolean
)


data class ExpandableCategory(
    val category: Category,
    val expanded: Boolean,
    val products: List<EnabledProduct>
)