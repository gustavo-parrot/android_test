package io.parrotsoftware.qatest.ui.list

import com.airbnb.epoxy.EpoxyController

class CategoryController(
    private val listener: CategoryListener
) : EpoxyController() {

    var categories: List<ExpandableCategory> = emptyList()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
//        categories.forEach { category ->
//            itemCategory {
//                id(category.category.id)
//                item(category)
//                onClick { _: View ->
//                    listener.onCategorySelected(category)
//                }
//            }
//
//            if (category.expanded) {
//                category.products.forEach { product ->
//                    itemProduct {
//                        id(product.product.id)
//                        item(product)
//                        onClick { _: View ->
//                            listener.onProductSelected(product)
//                        }
//                    }
//                }
//            }
//        }
    }
}
