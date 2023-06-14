package io.parrotsoftware.qatest.ui.list

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.parrotsoftware.qatest.R
import io.parrotsoftware.qatest.base.BaseFragment
import io.parrotsoftware.qatest.common.toast
import io.parrotsoftware.qatest.databinding.FragmentListBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment :
    BaseFragment(),
    CategoryListener,MenuProvider {

    private lateinit var binding: FragmentListBinding
    private val listViewModel: ListViewModel by viewModels()

    private val categoryController by lazy {
        CategoryController(this)
    }

    override fun setLayout(): Int = R.layout.fragment_list

    override fun setupView(view: View) {
        binding = FragmentListBinding.bind(view).apply {
            lifecycleOwner = this@ListFragment
            viewModel = listViewModel
            lifecycleOwner = this@ListFragment
            lifecycle.addObserver(listViewModel)
            recyclerProducts.adapter = categoryController.adapter
            swipeProducts.setOnRefreshListener { listViewModel.fetchProducts() }
        }
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        val menuHost : MenuHost = requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

    override fun initObservers() {
        lifecycleScope.launch {
            listViewModel.viewState.collect { viewState ->
                when (viewState) {
                    ListViewState.ErrorLoadingItems -> {
                        requireContext()
                            .toast(
                                getString(R.string.list_fragment_fetch_products_error)
                            )

                    }

                    ListViewState.ErrorUpdatingItem -> {
                        requireContext().toast(
                            getString(R.string.list_fragment_update_product_error)
                        )
                    }

                    ListViewState.Idle -> {
                        //nothing to do
                    }

                    ListViewState.ItemUpdated -> {
                        //Noting to do
                    }

                    ListViewState.Loading -> {
                        //TODO implement loading indicator
                    }

                    is ListViewState.ItemsLoaded -> {
                        categoryController.categories = viewState.categories
                    }
                }
            }
        }
    }

    override fun initFlow() {
        listViewModel.initView()
    }

    override fun onCategorySelected(category: ExpandableCategory) {
        listViewModel.categorySelected(category)
    }

    override fun onProductSelected(product: EnabledProduct) {
        listViewModel.productSelected(product)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.logout_menu, menu)

    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.menu_logout -> {
                //TODO("Implement")
            }
        }
        return true
    }

}