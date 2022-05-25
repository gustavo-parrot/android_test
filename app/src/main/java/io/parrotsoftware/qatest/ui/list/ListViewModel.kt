package io.parrotsoftware.qatest.ui.list

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.parrotsoftware.qatest.data.domain.Product
import io.parrotsoftware.qatest.data.repositories.ProductRepository
import io.parrotsoftware.qatest.data.repositories.UserRepository
import kotlinx.coroutines.launch

class ListViewModel(
    private var userRepository: UserRepository,
    private var productRepository: ProductRepository
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
            val credentials = userRepository.getCredentials()
            val store = userRepository.getStore()

            if (credentials.isError || store.isError) {
                _viewState.value = ListViewState.ErrorLoadingItems
                return@launch
            }

            val response = productRepository.getProducts(
                credentials.requiredResult.access,
                store.requiredResult.id
            )

            if (response.isError) {
                //TODO Get the expanded categories from the local data source
                _viewState.value = ListViewState.ErrorLoadingItems
                return@launch
            }

            products = response.requiredResult.toMutableList()
            val expandedCategories = createCategoriesList()
            _viewState.value = ListViewState.ItemsLoaded(expandedCategories)
        }
    }

    fun updateProduct(productId: String, isAvilable: Boolean) {
        viewModelScope.launch {
            val credentials = userRepository.getCredentials()

            if (credentials.isError) {
                _viewState.value = ListViewState.ErrorUpdatingItem
                return@launch
            }

            val response = productRepository.setProductState(
                credentials.requiredResult.access,
                productId,
                isAvilable
            )

            if (response.isError) {
                _viewState.value = ListViewState.ErrorUpdatingItem
                return@launch
            }

            _viewState.value = ListViewState.ItemUpdated
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