package io.parrotsoftware.qatest.ui.list

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.parrotsoftware.qatest.data.models.Result
import io.parrotsoftware.qatest.domain.models.Product
import io.parrotsoftware.qatest.data.repositories.ProductRepository
import io.parrotsoftware.qatest.data.repositories.UserRepository
import io.parrotsoftware.qatest.usecase.product.GetProductsUseCase
import io.parrotsoftware.qatest.usecase.product.SetProductStateUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val productsUseCase: GetProductsUseCase,
    private val setProductStateUseCase: SetProductStateUseCase
) : ViewModel(), LifecycleObserver {

    private val _viewState = MutableLiveData<ListViewState>()
    fun getViewState() = _viewState

    val isLoading: LiveData<Boolean> = Transformations.map(_viewState) {
        it is ListViewState.Loading
    }

    private var products = mutableListOf<Product>()
    private val categoriesExpanded = mutableMapOf<String, Boolean>()


    fun initView() {
        fetchProducts()
    }

    fun fetchProducts() {
        _viewState.value = ListViewState.Loading

        viewModelScope.launch {
            when (val result = productsUseCase()) {
                is Result.Success -> {
                    products = result.data.toMutableList()
                    val expandedCategories = createCategoriesList()
                    _viewState.value = ListViewState.ItemsLoaded(expandedCategories)

                }

                is Result.Error -> {
                    _viewState.value = ListViewState.ErrorLoadingItems
                }
            }
        }
    }

    private fun updateProduct(productId: String, isAvilable: Boolean) {
        viewModelScope.launch {
            when (val result = setProductStateUseCase(productId, isAvilable)) {
                is Result.Success -> {
                    _viewState.value = ListViewState.ItemUpdated
                }

                is Result.Error -> {
                    _viewState.value = ListViewState.ErrorUpdatingItem
                    return@launch
                }
            }
        }
    }

    fun categorySelected(category: ExpandableCategory) {
        val currentState = categoriesExpanded[category.category.id] ?: false
        categoriesExpanded[category.category.id] = !currentState
        val expandedCategories = createCategoriesList()
        _viewState.value = ListViewState.ItemsLoaded(expandedCategories)
    }

    fun productSelected(product: EnabledProduct) {
        val nextState = product.enabled.not()
        val index = products.indexOfFirst { it.id == product.product.id }
        products[index] = product.product.copy(isAvailable = nextState)
        val expandedCategories = createCategoriesList()
        _viewState.value = ListViewState.ItemsLoaded(expandedCategories)
        updateProduct(product.product.id, nextState)
    }

    private fun createCategoriesList(): List<ExpandableCategory> {
        // Get Categories from products
        val categories = products
            .map { it.category }
            .distinctBy { it.id }
            .sortedBy { it.position }
        val groupedProducts = products.groupBy { it.category.id }

        return categories.map { category ->
            val productGroup = groupedProducts[category.id]?.map { product ->
                EnabledProduct(product, product.isAvailable)
            } ?: emptyList()

            ExpandableCategory(
                category,
                categoriesExpanded[category.id] ?: false,
                productGroup
            )
        }
    }
}
