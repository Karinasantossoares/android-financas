package com.example.desafioandroidmobills.di

import com.example.desafioandroidmobills.repository.AuthenticationRepository
import com.example.desafioandroidmobills.repository.TransactionRepository
import com.example.desafioandroidmobills.useCase.AuthenticationUseCase
import com.example.desafioandroidmobills.useCase.TransactionUseCase
import com.example.desafioandroidmobills.viewModel.AuthenticationViewModel
import com.example.desafioandroidmobills.viewModel.TransactionViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val modulesTransaction = module {
    single { TransactionRepository(androidApplication(), get(),get()) }
    single { AuthenticationRepository(androidApplication(),get()) }
    single { TransactionUseCase(get(),get()) }
    single { AuthenticationUseCase(get(),get()) }
    single { FirebaseDatabase.getInstance().reference }
    single { FirebaseAuth.getInstance() }
    viewModel { TransactionViewModel(androidContext(), get()) }
    viewModel { AuthenticationViewModel(androidContext(), get()) }
}