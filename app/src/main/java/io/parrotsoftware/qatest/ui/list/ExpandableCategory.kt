package io.parrotsoftware.qatest.ui.list

import io.parrotsoftware.qa_data.CategoryD
import io.parrotsoftware.qa_data.ProductD


data class EnabledProduct(
    val product: ProductD,
    val enabled: Boolean
)


data class ExpandableCategory(
    val category: CategoryD,
    val expanded: Boolean,
    val products: List<EnabledProduct>
)