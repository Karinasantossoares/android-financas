package com.example.desafioandroidmobills.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.desafioandroidmobills.R
import com.example.desafioandroidmobills.databinding.FragmentRegisterBinding
import com.example.desafioandroidmobills.viewModel.AuthenticationViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel


class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: AuthenticationViewModel by sharedViewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.onDestroy()

        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmationPassword = binding.etConfirmationPassword.text.toString()
            viewModel.registerUser(email, password, confirmationPassword)
        }

        viewModel.successLoginLiveData.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(R.id.action_registerFragment_to_mainActivity)
            requireActivity().finish()
        })

        viewModel.errorRegisterLiveData.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

        viewModel.loadLiveData.observe(viewLifecycleOwner, Observer {
            binding.pbLoadRegister.isVisible = it
            binding.btnRegister.isVisible = !it
        })

    }

}


