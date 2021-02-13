package com.example.desafioandroidmobills.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.desafioandroidmobills.R
import com.example.desafioandroidmobills.useCase.AuthenticationUseCase

class AuthenticationViewModel(
    private val context: Context,
    private val authenticationUseCase: AuthenticationUseCase
) : ViewModel() {

    var loadLiveData = MutableLiveData<Boolean>()
    var successLoginLiveData = MutableLiveData<Any>()
    var errorLoginLiveData = MutableLiveData<String>()
    var errorRegisterLiveData = MutableLiveData<String>()


    fun login(
        email: String,
        password: String
    ) {
        loadLiveData.value = true
        authenticationUseCase.logInUser(
            email, password,
            success = {
                loadLiveData.value = false
                successLoginLiveData.value = Any()

            },
            error = {
                loadLiveData.value = false
                errorLoginLiveData.value = it.localizedMessage

            })
    }

    fun registerUser(
        email: String,
        password: String,
        confirmationPassword: String
    ) {
        loadLiveData.value = true
        authenticationUseCase.registerUser(
            email,
            password,
            confirmationPassword,
            success = {
                loadLiveData.value = false
                login(email, password)
            },
            error = {
                loadLiveData.value = false
                errorRegisterLiveData.value = it.localizedMessage

            }
        )

    }

    fun onDestroy() {
         loadLiveData = MutableLiveData()
         errorRegisterLiveData =MutableLiveData()
    }

}