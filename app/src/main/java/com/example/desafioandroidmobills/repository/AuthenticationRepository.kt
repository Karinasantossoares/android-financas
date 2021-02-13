package com.example.desafioandroidmobills.repository

import android.app.Activity
import android.content.Context
import com.example.desafioandroidmobills.ui.activity.TransactionActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import java.lang.Exception
import java.util.concurrent.Executor

class AuthenticationRepository(context: Context, private var auth: FirebaseAuth) {


    fun login(
        email: String,
        password: String,
        success: () -> Unit,
        error: (Exception) -> Unit
    ) {
        auth.signOut()
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            success.invoke()
        }.addOnFailureListener {
            error.invoke(it)
        }
    }

    fun registerUser(
        email: String,
        password: String,
        success: () -> Unit,
        error: (Exception) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            success.invoke()
        }.addOnFailureListener {
            error.invoke(it)
        }
    }
}

