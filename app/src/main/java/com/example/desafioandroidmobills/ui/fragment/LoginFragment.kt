package com.example.desafioandroidmobills.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.desafioandroidmobills.R
import com.example.desafioandroidmobills.databinding.FragmentLoginBinding
import com.example.desafioandroidmobills.viewModel.AuthenticationViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.viewmodel.ext.android.sharedViewModel


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: AuthenticationViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnEnter.setOnClickListener {
            val email = et_email.text.toString()
            val password = et_password.text.toString()
            viewModel.login(email, password)
        }

        binding.tvNewRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        viewModel.loadLiveData.observe(viewLifecycleOwner, Observer {
            binding.pbLoad.isVisible = it
            binding.btnEnter.isVisible = !it
        })

        viewModel.successLoginLiveData.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
            requireActivity().finish()
        })

        viewModel.errorLoginLiveData.observe(viewLifecycleOwner, Observer {
            binding.tvMessageError.isVisible = true
            binding.tvMessageError.text = it
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.onDestroy()

    }

}