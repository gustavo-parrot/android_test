package io.parrotsoftware.qatest.ui.list

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
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
    CategoryListener {

    private lateinit var binding: FragmentListBinding
    private val listViewModel: ListViewModel by viewModels()

    private val categoryController by lazy {
        CategoryController(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.logout_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> {
                TODO("Implement")
            }
        }
        return super.onOptionsItemSelected(item)
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
    }

    override fun initObservers() {
        lifecycleScope.launch {
            listViewModel.viewState.collect { viewState ->
                when (viewState) {
                    ListViewState.ErrorLoadingItems -> {
                        requireContext().toast("Error al cargar los productos")

                    }

                    ListViewState.ErrorUpdatingItem -> {
                        requireContext().toast("Error al actualizar el producto")
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

}