package io.parrotsoftware.qatest.ui.login

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.parrotsoftware.qatest.R
import io.parrotsoftware.qatest.base.BaseFragment
import io.parrotsoftware.qatest.common.toast
import io.parrotsoftware.qatest.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    private val loginViewModel: LoginViewModel by viewModels()

    private lateinit var binding: FragmentLoginBinding
    override fun setLayout(): Int = R.layout.fragment_login

    override fun setupView(view: View) {
        binding = FragmentLoginBinding.bind(view).apply {
            viewModel = loginViewModel
            lifecycleOwner = this@LoginFragment
            lifecycle.addObserver(loginViewModel)
        }
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

    }

    override fun initObservers() {
        lifecycleScope.launch {
            loginViewModel.viewState.collect { viewState ->
                when (viewState) {
                    LoginViewState.Idle -> {//Noting to do
                    }

                    LoginViewState.LoginError -> {
                        requireContext().toast("Error al iniciar sesión")
                    }

                    LoginViewState.LoginSuccess -> {
                        findNavController().navigate(
                            LoginFragmentDirections.actionLoginFragmentToListFragment()
                        )
                        loginViewModel.navigated()
                    }
                }
            }

        }
    }

    override fun initFlow() {
        loginViewModel.initView()
    }

}