package io.parrotsoftware.qatest.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.parrotsoftware.qa_network.interactors.impl.NetworkInteractorImpl
import io.parrotsoftware.qatest.MainActivity
import io.parrotsoftware.qatest.R
import io.parrotsoftware.qatest.databinding.FragmentListBinding
import io.parrotsoftware.qatest.common.observe
import io.parrotsoftware.qatest.common.toast
import io.parrotsoftware.qatest.data.managers.impl.UserManagerImpl
import io.parrotsoftware.qatest.data.repositories.impl.ProductRepositoryImpl
import io.parrotsoftware.qatest.data.repositories.impl.UserRepositoryImpl
import io.parrotsoftware.qatest.ui.login.LoginFragmentDirections

@AndroidEntryPoint
class ListFragment :
    Fragment(),
    CategoryListener {

    private lateinit var viewModel: ListViewModel
    private lateinit var binding: FragmentListBinding

    private val categoryController by lazy {
        CategoryController(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)

        binding.viewModel = viewModel

        lifecycle.addObserver(viewModel)
        observe(viewModel.getViewState(), ::onViewState)

        binding.recyclerProducts.adapter = categoryController.adapter
        binding.swipeProducts.setOnRefreshListener { viewModel.fetchProducts() }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.logout_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> {
                viewModel.clearAuth()
                clearBackstack()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onViewState(state: ListViewState?) {
        when (state) {
            ListViewState.ErrorLoadingItems -> {
                requireContext().toast("Error al cargar los productos")
            }

            is ListViewState.ItemsLoaded -> {
                categoryController.categories = state.categories
            }

            ListViewState.ErrorUpdatingItem -> {
                requireContext().toast("Error al actualizar el producto")
            }

            else -> {}
        }
    }

    override fun onCategorySelected(category: ExpandableCategory) {
        viewModel.categorySelected(category)
    }

    override fun onProductSelected(product: EnabledProduct) {
        viewModel.productSelected(product)
    }


    private fun clearBackstack() {
        findNavController().popBackStack(R.id.listFragment, true)
        findNavController().navigate(
            R.id.loginFragment,
            null,
            NavOptions.Builder()
                .setPopUpTo(R.id.listFragment, true)
                .build()
        )
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
    }
}