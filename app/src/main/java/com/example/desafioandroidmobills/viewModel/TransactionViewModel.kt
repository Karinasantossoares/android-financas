package com.example.desafioandroidmobills.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.desafioandroidmobills.R
import com.example.desafioandroidmobills.data.Transaction
import com.example.desafioandroidmobills.useCase.TransactionUseCase
import java.util.*

class TransactionViewModel(
    private val context: Context,
    private val useCase: TransactionUseCase
) : ViewModel() {
    var loadLiveData = MutableLiveData<Boolean>()
    var successListTransactionsLiveData = MutableLiveData<List<Transaction>>()
    var selectedTransactionsLiveData = MutableLiveData<Transaction>()
    var successSaveLiveData = MutableLiveData<String>()
    var deleteTransactionSuccessLiveData = MutableLiveData<String>()
    var errorLiveData = MutableLiveData<String>()
    var errorRegisterLiveData = MutableLiveData<String>()
    var pieChartOptions = MutableLiveData<FloatArray>()

    fun saveOrUpdateTransaction(transaction: Transaction) {
        if (selectedTransactionsLiveData.value != null) {
            updateTransaction(transaction)
        } else {
            saveTransaction(transaction)
        }
    }

    private fun saveTransaction(transaction: Transaction) {
        loadLiveData.value = true
        useCase.saveTransaction(transaction,
            error = {
                loadLiveData.value = false
                errorRegisterLiveData.value = it.localizedMessage
            },
            success = {
                loadLiveData.value = false
                successSaveLiveData.value = context.getString(R.string.message_new_regsister_ok)

            }
        )
    }

    private fun updateTransaction(transaction: Transaction) {
        loadLiveData.value = true
        transaction.id = selectedTransactionsLiveData.value?.id
        useCase.updateTransaction(transaction,
            error = {
                loadLiveData.value = false
                errorRegisterLiveData.value = it.localizedMessage
            },
            success = {
                loadLiveData.value = false
                successSaveLiveData.value = context.getString(R.string.message_updated_regsister_ok)

            }
        )
    }

    fun deleteTransaction(transaction: Transaction) {
        loadLiveData.value = true
        transaction.id?.let { id ->
            useCase.deleteTransaction(id, success = {
                loadLiveData.value = false
                deleteTransactionSuccessLiveData.value =
                    context.getString(R.string.message_item_delet)

            },
                error = {
                    loadLiveData.value = true
                    errorLiveData.value = context.getString(R.string.error_update_item)

                })

        }
    }

    fun findAllTransactions() {
        loadLiveData.value = true
        useCase.findAllTransaction(
            success = {
                loadLiveData.value = false
                if (it.isNotEmpty()) {
                    val profit = it.filter { transactionPaid ->
                        transactionPaid.paid == false
                    }.map { transactionValue ->
                        transactionValue.value
                    }.toFloatArray().sum()

                    val paids = it.filter { transaction ->
                        transaction.paid != false
                    }.map { transactionValue ->
                        transactionValue.value
                    }.toFloatArray().sum()

                    pieChartOptions.value =
                        floatArrayOf(profit, paids)
                    successListTransactionsLiveData.value = it
                } else {
                    errorLiveData.value = context.getString(R.string.message_error_list_empty)

                }

            },
            error = {
                loadLiveData.value = false
                errorRegisterLiveData.value = it.localizedMessage
            })
    }

    fun selectTransaction(transaction: Transaction) {
        selectedTransactionsLiveData.value = transaction
    }

    fun clearSelected() {
        selectedTransactionsLiveData = MutableLiveData()
    }

    fun onDestroy() {
        errorRegisterLiveData = MutableLiveData()
        loadLiveData = MutableLiveData()
        successSaveLiveData = MutableLiveData()
        deleteTransactionSuccessLiveData = MutableLiveData()
        errorLiveData = MutableLiveData()
    }


}
