package io.parrotsoftware.qatest.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.parrotsoftware.qa_network.interactors.impl.NetworkInteractorImpl
import io.parrotsoftware.qatest.databinding.FragmentLoginBinding
import io.parrotsoftware.qatest.common.observe
import io.parrotsoftware.qatest.common.toast
import io.parrotsoftware.qatest.data.managers.impl.UserManagerImpl
import io.parrotsoftware.qatest.data.repositories.impl.UserRepositoryImpl
@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)
        binding.lifecycleOwner = this

//        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
//
//        // TODO Inject
//        viewModel.userManager = UserManagerImpl(requireContext())
//        viewModel.userRepository = UserRepositoryImpl(viewModel.userManager, NetworkInteractorImpl())

        binding.viewModel = viewModel

        lifecycle.addObserver(viewModel)
        observe(viewModel.getViewState(), ::onViewState)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initView()
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }

    private fun onViewState(state: LoginViewState?) {
        when (state) {
            LoginViewState.LoginError -> {
                requireContext().toast("Error al iniciar sesión")
            }
            LoginViewState.LoginSuccess -> {
                findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToListFragment()
                )
                viewModel.navigated()
            }
            else -> {}
        }
    }
}