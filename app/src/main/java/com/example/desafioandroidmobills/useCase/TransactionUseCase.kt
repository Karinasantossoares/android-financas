package com.example.desafioandroidmobills.useCase

import android.content.Context
import com.example.desafioandroidmobills.R
import com.example.desafioandroidmobills.data.Transaction
import com.example.desafioandroidmobills.repository.TransactionRepository
import com.google.firebase.database.DatabaseException


class TransactionUseCase(
    private val context: Context,
    private val repository: TransactionRepository
) {

    fun saveTransaction(
        transaction: Transaction,
        success: () -> Unit,
        error: (Exception) -> Unit
    ) {

        if (transaction.paid == null ||
            transaction.date == null ||
            transaction.description.isEmpty()
        ) {
            error.invoke(Exception(context.getString(R.string.message_data_empty)))
        } else {
            repository.saveTransaction(transaction, success, error)
        }

    }

    fun updateTransaction(
        transaction: Transaction,
        success: () -> Unit,
        error: (Exception) -> Unit
    ) {
        if (transaction.paid == null ||
            transaction.date == null ||
            transaction.description.isEmpty()
        ) {
            error.invoke(Exception(context.getString(R.string.message_data_empty)))
        } else {
            repository.updateTransaction(transaction, success, error)
        }
    }

    fun deleteTransaction(
        idTransaction: String,
        success: () -> Unit,
        error: (Exception) -> Unit
    ) {
        repository.deleteTransaction(idTransaction, success, error)
    }

    fun findAllTransaction(
        success: (List<Transaction>) -> Unit,
        error: (DatabaseException) -> Unit
    ) {
        repository.findAllTransaction(success, error)
    }

}