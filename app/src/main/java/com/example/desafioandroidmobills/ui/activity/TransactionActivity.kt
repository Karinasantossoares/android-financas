package com.example.desafioandroidmobills.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.example.desafioandroidmobills.R

class TransactionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.transaction_activity)
    }

    override fun onSupportNavigateUp() = findNavController(R.id.hostTransaction).navigateUp()

}